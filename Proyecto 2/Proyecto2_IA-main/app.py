from flask import Flask, request, jsonify, send_from_directory, render_template
import os
import uuid 
from models.ocr_model import htr_pipeline
from models.tts_model import tts_pipeline
from models.utils import postprocess_text

app = Flask(__name__)
UPLOAD_FOLDER = 'uploads'
AUDIO_FOLDER = 'static/audio'
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER


os.makedirs(UPLOAD_FOLDER, exist_ok=True)
os.makedirs(AUDIO_FOLDER, exist_ok=True)


@app.route('/', methods=['GET'])
def index():
   
    return render_template('index.html')


@app.route('/convertir', methods=['POST'])
def convertir_manuscrito_a_voz():
   
    if 'imagen' not in request.files:
        return jsonify({"error": "No se encontró el archivo de imagen en la solicitud."}), 400
    
    file = request.files['imagen']
    if file.filename == '':
        return jsonify({"error": "No se seleccionó ningún archivo."}), 400

   
    selected_voice = request.form.get('voice', 'es-ES-AlvaroNeural')

   
    unique_filename = str(uuid.uuid4()) + ".png"
    image_path = os.path.join(app.config['UPLOAD_FOLDER'], unique_filename)
    file.save(image_path)
    
    try:
      
        transcribed_text = htr_pipeline.transcribe(image_path)
        
        
        final_text = postprocess_text(transcribed_text)
        
        audio_filename = str(uuid.uuid4()) + ".mp3"
        audio_relative_path = os.path.join("audio", audio_filename)

       
        audio_full_path = tts_pipeline.synthesize(final_text, audio_relative_path, voice=selected_voice)

        if audio_full_path is None:
            return jsonify({"error": "Error durante la síntesis de voz."}), 500

        audio_url = f"/static/{os.path.relpath(audio_full_path, 'static')}"

        return jsonify({
            "status": "success",
            "texto_transcrito": transcribed_text,
            "texto_corregido": final_text,
            "audio_url": audio_url
        }), 200

    except Exception as e:
        print(f"Error crítico en el pipeline: {e}")
        return jsonify({"error": f"Error interno del servidor: {e}"}), 500
    
    finally:
      
        if os.path.exists(image_path):
            os.remove(image_path)

if __name__ == '__main__':
  
    app.run(debug=True, host='127.0.0.1', port=5000)