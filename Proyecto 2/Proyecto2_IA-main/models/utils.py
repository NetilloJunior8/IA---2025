import re
import language_tool_python

# Inicializar LanguageTool para español
_tool = language_tool_python.LanguageTool("es")

def _basic_clean(text: str) -> str:
    """
    Limpiezas básicas:
    - Quitar espacios múltiples
    - Normalizar saltos de línea
    - Quitar caracteres de control extraños
    """
    if text is None:
        return ""

    # Reemplazar múltiples espacios por uno
    text = re.sub(r"\s+", " ", text)
    # Quitar espacios al inicio y final
    text = text.strip()
    return text

def _spell_and_grammar_correction(text: str) -> str:
    """
    Corrección ortográfica y gramatical con LanguageTool.
    """
    if not text:
        return text
    matches = _tool.check(text)
    corrected = language_tool_python.utils.correct(text, matches)
    return corrected

def postprocess_text(text: str) -> str:
    """
    Pipeline de post-procesamiento:
    1. Limpieza básica
    2. Corrección ortográfica/gramatical
    3. (Opcional) Otras normalizaciones (números, abreviaturas, etc.)
    """
    cleaned = _basic_clean(text)
    corrected = _spell_and_grammar_correction(cleaned)
    return corrected