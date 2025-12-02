import cv2
import os
import argparse
import time
from datetime import datetime

parser = argparse.ArgumentParser()
parser.add_argument('--output', type=str, default='../dataset', help='Carpeta donde guardar clases')
parser.add_argument('--class_name', type=str, required=True, help='Nombre de la clase (ej: yo, famoso1)')
parser.add_argument('--interval', type=float, default=0.1, help='Tiempo entre fotos (segundos)')
args = parser.parse_args()

output_path = os.path.abspath(os.path.join(os.path.dirname(__file__), args.output))
class_dir = os.path.join(output_path, args.class_name)
os.makedirs(class_dir, exist_ok=True)

cap = cv2.VideoCapture(0)

capturing = False
last_capture_time = 0
count = 0

print("=== Captura automática de imágenes ===")
print("Presiona SPACE para iniciar/detener la captura")
print("Presiona ESC para salir")
print("----------------------------------------")

while True:
    ret, frame = cap.read()
    if not ret:
        break

    display = frame.copy()

    # Indicador visual
    status_text = "Capturando..." if capturing else "Pausa"
    color = (0, 255, 0) if capturing else (0, 0, 255)
    cv2.putText(display, f"{status_text} | Clase: {args.class_name} | Fotos: {count}",
                (10, 30), cv2.FONT_HERSHEY_SIMPLEX, 0.7, color, 2)

    cv2.imshow("Captura Automatica", display)
    key = cv2.waitKey(1) & 0xFF

    # Toggle de captura con SPACE
    if key == 32:  # SPACE
        capturing = not capturing
        state = "Iniciando captura..." if capturing else "Deteniendo captura..."
        print(state)
        time.sleep(0.3)  # evita doble toggle accidental

    if key == 27:  # ESC
        print("Saliendo...")
        break

    # Guardar imágenes automáticamente
    if capturing:
        current_time = time.time()
        if current_time - last_capture_time >= args.interval:
            filename = f"{args.class_name}_{datetime.now().strftime('%Y%m%d_%H%M%S_%f')}.jpg"
            cv2.imwrite(os.path.join(class_dir, filename), frame)
            count += 1
            last_capture_time = current_time
            print(f"[+] Capturada: {filename}")

cap.release()
cv2.destroyAllWindows()
