
const img = document.getElementById("video-stream");
const toggleBtn = document.getElementById("toggleStreamBtn");
const snapshotBtn = document.getElementById("snapshotBtn");
const statusEl = document.getElementById("status");
const yearEl = document.getElementById("year");
const snapshotCanvas = document.getElementById("snapshotCanvas");
const streamURL = img?.getAttribute("src") || "/video_feed";
let paused = false;
function setStatus(text) {
  if (statusEl) statusEl.textContent = text;
}

function resumeStream() {
  img.src = `${streamURL}?t=${Date.now()}`; 
  paused = false;
  toggleBtn.textContent = "Pausar camara";
  setStatus("Conectado");
}

function pauseStream() {
  img.src = "";
  paused = true;
  toggleBtn.textContent = "Reanudar camara";
  setStatus("Pausado");
}

function toggleStream() {
  if (paused) resumeStream();
  else pauseStream();
}

function downloadBlob(blob, filename) {
  const url = URL.createObjectURL(blob);
  const a = document.createElement("a");
  a.href = url;
  a.download = filename;
  document.body.appendChild(a);
  a.click();
  a.remove();
  URL.revokeObjectURL(url);
}

async function takeSnapshot() {
  if (!img.complete || !img.naturalWidth) {
    setStatus("Esperando al stream…");
    return;
  }
  const w = img.naturalWidth;
  const h = img.naturalHeight;
  snapshotCanvas.width = w;
  snapshotCanvas.height = h;
  const ctx = snapshotCanvas.getContext("2d");
  ctx.drawImage(img, 0, 0, w, h);

  snapshotCanvas.toBlob((blob) => {
    if (blob) downloadBlob(blob, `captura_${Date.now()}.jpg`);
  }, "image/jpeg", 0.92);
}

function wireEvents() {
  toggleBtn?.addEventListener("click", toggleStream);
  snapshotBtn?.addEventListener("click", takeSnapshot);

  img?.addEventListener("load", () => setStatus("Conectado"));
  img?.addEventListener("error", () => setStatus("Error de conexión"));

  if (yearEl) yearEl.textContent = new Date().getFullYear();
}

document.addEventListener("DOMContentLoaded", wireEvents);