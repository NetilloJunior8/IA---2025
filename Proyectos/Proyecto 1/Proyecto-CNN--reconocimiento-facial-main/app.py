# app.py
import cv2
import numpy as np
from flask import Flask, render_template, Response
from tensorflow.keras.models import load_model
import pickle
import os

app = Flask(__name__)
MODEL_PATH = os.path.join('models', 'face_classifier_final.h5')
LABEL_PATH = os.path.join('models', 'label_encoder.pkl')

# Cargar modelo y label encoder
model = load_model(MODEL_PATH)
with open(LABEL_PATH, 'rb') as f:
    label_map = pickle.load(f)
# invert mapping: index->label
inv_label_map = {v:k for k,v in label_map.items()}

# Haar cascade para deteccion facial (incluye archivo en la misma carpeta o usar el de OpenCV)
cascade_path = cv2.data.haarcascades + "haarcascade_frontalface_default.xml"
face_cascade = cv2.CascadeClassifier(cascade_path)

video = cv2.VideoCapture(0)

def generate_frames():
    while True:
        success, frame = video.read()
        if not success:
            break

        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
        faces = face_cascade.detectMultiScale(gray, scaleFactor=1.1, minNeighbors=5,
                                              minSize=(60, 60), flags=cv2.CASCADE_SCALE_IMAGE)

        for (x, y, w, h) in faces:
            face_img = frame[y:y+h, x:x+w]
            try:
                face_resized = cv2.resize(face_img, (224,224))
            except Exception:
                continue
            face_rgb = cv2.cvtColor(face_resized, cv2.COLOR_BGR2RGB)
            face_array = face_rgb.astype("float32") / 255.0
            face_array = np.expand_dims(face_array, axis=0)

            preds = model.predict(face_array)
            idx = np.argmax(preds)
            prob = preds[0][idx]
            label = inv_label_map.get(idx, 'Unknown')

            text = f"{label}: {prob*100:.1f}%"
            # Dibujar bounding box y etiqueta
            cv2.rectangle(frame, (x,y), (x+w, y+h), (0,255,0), 2)
            cv2.putText(frame, text, (x, y-10), cv2.FONT_HERSHEY_SIMPLEX, 0.7, (0,255,0), 2)

        # codificar frame a JPEG
        ret, buffer = cv2.imencode('.jpg', frame)
        frame_bytes = buffer.tobytes()
        # stream multipart
        yield (b'--frame\r\n'
               b'Content-Type: image/jpeg\r\n\r\n' + frame_bytes + b'\r\n')

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/video_feed')
def video_feed():
    return Response(generate_frames(),
                    mimetype='multipart/x-mixed-replace; boundary=frame')

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
