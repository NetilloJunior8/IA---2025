import os
import azure.cognitiveservices.speech as speechsdk

BASE_AUDIO_DIR = os.path.join("static", "audio")
os.makedirs(BASE_AUDIO_DIR, exist_ok=True)


VALID_VOICES = {
    "es-ES-AlvaroNeural",
    "es-ES-ElviraNeural",
    "es-MX-DaliaNeural",
    "es-MX-JorgeNeural",
}

class TTSPipeline:
    def __init__(self):
       
        self.speech_key = os.getenv("AZURE_SPEECH_KEY")
        self.speech_region = os.getenv("AZURE_SPEECH_REGION")

        if not self.speech_key or not self.speech_region:
            raise RuntimeError(
                "[TTS] Faltan variables de entorno AZURE_SPEECH_KEY o AZURE_SPEECH_REGION"
            )

       
        self.speech_config = speechsdk.SpeechConfig(
            subscription=self.speech_key,
            region=self.speech_region
        )

      
        self.speech_config.set_speech_synthesis_output_format(
            speechsdk.SpeechSynthesisOutputFormat.Audio16Khz32KBitRateMonoMp3
        )

    def synthesize(
        self,
        text: str,
        relative_audio_path: str,
        voice: str = "es-ES-AlvaroNeural"
    ) -> str:
      
        if not text or not text.strip():
            raise ValueError("[TTS] Texto vacío, no se puede sintetizar.")

       
        if voice not in VALID_VOICES:
            voice = "es-ES-AlvaroNeural"

        
        self.speech_config.speech_synthesis_voice_name = voice

        
        full_path = os.path.join("static", relative_audio_path)
        os.makedirs(os.path.dirname(full_path), exist_ok=True)

        # Forzar extensión .mp3
        root, _ext = os.path.splitext(full_path)
        full_path = root + ".mp3"

        audio_config = speechsdk.audio.AudioOutputConfig(filename=full_path)
        synthesizer = speechsdk.SpeechSynthesizer(
            speech_config=self.speech_config,
            audio_config=audio_config
        )

        result = synthesizer.speak_text_async(text).get()

        if result.reason == speechsdk.ResultReason.SynthesizingAudioCompleted:
            return full_path

        # Manejo de errores de Azure
        if result.reason == speechsdk.ResultReason.Canceled:
            cancellation_details = result.cancellation_details
            raise RuntimeError(
                f"[TTS] Síntesis cancelada: {cancellation_details.reason}, "
                f"detalles: {cancellation_details.error_details}"
            )

        raise RuntimeError("[TTS] Error desconocido durante la síntesis.")

# Instancia global que usará app.py
tts_pipeline = TTSPipeline()