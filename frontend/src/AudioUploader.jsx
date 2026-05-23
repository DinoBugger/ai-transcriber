import { useState } from "react";
import axios from "axios";
import "./AudioUploader.css";

export default function AudioUploader() {
  const [file, setFile] = useState(null);
  const [result, setResult] = useState("");
  const [isWaiting, setIsWaiting] = useState(false);

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleUploadFile = async (e) => {
    if (!file) {
      alert("Please add in an audio file");
      return;
    }
    const formData = new FormData();
    formData.append("file", file);

    setIsWaiting(true);
    setResult("");

    try {
      let response = await axios.post("http://localhost:8080/api/transcribe", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });

      if (response && response.data) {
        setResult(response.data);
      }
    } catch (error) {
      setResult("Error occurs in uploading ", error);
    } finally {
      setIsWaiting(false);
    }
  };

  const countWord = (text) => {
    if (!text && typeof text == String) return 0;
    return text
      .trim()
      .split(/\s+/)
      .filter((word) => word.length > 0).length;
  };

  const handleDownloadTxt = () => {
    if (!result) return;

    const blob = new Blob([result], { type: "text/plain;charset=utf-8" });

    const url = URL.createObjectURL(blob);

    const link = document.createElement("a");
    link.href = url;

    const originalName = file?.name ? file.name.substring(0, file.name.lastIndexOf(".")) : "transcription";
    link.download = `${originalName}_result.txt`;

    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
    URL.revokeObjectURL(url);
  };

  const count = countWord(result);
  return (
    <div className="container">
      <h1>Audio To Text Transcriber</h1>
      <div className="file-input">
        <input type="file" accept="audio/*" onChange={handleFileChange} />
      </div>

      <button className="upload-btn" onClick={handleUploadFile} disabled={isWaiting}>
        {!isWaiting ? "Upload And Transcribe" : "Almost done ...."}
      </button>

      {result && (
        <div className="trans-result">
          <div className="result-header">
            <h3>Transcription Result ({count} words)</h3>

            {/* CONDITION: IF text is more than 100 then download file.txt */}
            {count > 3 && (
              <button className="download-txt-btn" onClick={handleDownloadTxt}>
                📥 Download file .txt
              </button>
            )}
          </div>
          <p style={{ whiteSpace: "pre-wrap" }}>{result}</p>
        </div>
      )}
    </div>
  );
}
