import os
import cv2
import numpy as np
from PIL import Image
import easyocr 



EASYOCR_LANGS = ['es'] 

_reader = easyocr.Reader(EASYOCR_LANGS)

def _read_image_bgr(image_path: str):
    img_bgr = cv2.imread(image_path)
    if img_bgr is None:
        raise FileNotFoundError(f"[HTR] No se pudo leer la imagen: {image_path}")
    return img_bgr


def _preprocess_for_ocr(img_bgr):
    """
    Preprocesamiento ligero para ayudar a EasyOCR.
    """
    gray = cv2.cvtColor(img_bgr, cv2.COLOR_BGR2GRAY)

    # Aumentar contraste con CLAHE
    clahe = cv2.createCLAHE(clipLimit=3.0, tileGridSize=(8, 8))
    gray = clahe.apply(gray)

    # Reducir ruido
    gray = cv2.fastNlMeansDenoising(gray, None, h=10)

    return gray


class HTRPipeline:

    def __init__(self):
        
        pass

    def transcribe(self, image_path: str) -> str:
        img_bgr = _read_image_bgr(image_path)
        gray = _preprocess_for_ocr(img_bgr)

       
        results = _reader.readtext(gray, detail=0)

        text = " ".join(results).strip()

        if not text:
            text = "[NO SE PUDO RECONOCER EL TEXTO MANUSCRITO DE LA IMAGEN]"

        return text

ocr_pipeline = HTRPipeline()
htr_pipeline = HTRPipeline()