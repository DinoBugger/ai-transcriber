# Audio-Transcriber

A mini AI-driven project that transcribes audio files into text.

## Main Features

- Upload audio files from the local device
- Transcribe audio to text using OpenAI Whisper
- Download the result as a text file when the output is too long

## Technology Stack

The project is split into two parts: a backend server and a frontend user interface.

### Frontend

- Vite
- React JS
- CSS
- Axios

### Backend

- Spring Boot
- OpenAI
- Whisper-1

## 📂 Directory Structure

```text
audio-transcriber/
├── backend/
│   ├── pom.xml
│   ├── mvnw
│   ├── mvnw.cmd
│   └── src/
│       ├── main/
│       │   ├── java/com/audio/transcriber/
│       │   │   ├── AudioTranscriberApplication.java
│       │   │   ├── configuration/
│       │   │   ├── controller/
│       │   │   └── util/
│       │   └── resources/
│       │       └── application.properties
│       └── test/
│           └── java/com/audio/transcriber/
└── frontend/
	 ├── package.json
	 ├── vite.config.js
	 └── src/
		  ├── App.jsx
		  ├── AudioUploader.jsx
		  └── main.jsx
```

## 🚀 Setting and Executing

### 1. Requirement

Make sure you have installed:

- Java 21
- Maven Wrapper or Maven
- Node.js 18+ and npm
- An OpenAI API key

### 2. Set up backend

1. Move into the backend folder:

   ```bash
   cd backend
   ```

2. Set your OpenAI API key in the environment before starting the application:

   ```bash
   $env:OPENAI_API_KEY="your_openai_api_key"
   ```

   If you prefer a file-based setup, you can also use a local `.env` file as supported by the backend configuration.
   OPENAI_API_KEY=your_openai_api_key

3. Run the backend server:

   ```bash
   .\mvnw.cmd spring-boot:run
   ```

The backend will run on `http://localhost:8080`.

### 3. Set up frontend

1. Open a new terminal and move into the frontend folder:

   ```bash
   cd frontend
   ```

2. Install dependencies:

   ```bash
   npm install
   ```

3. Start the frontend dev server:

   ```bash
   npm run dev
   ```

The frontend will run on `http://localhost:5173`.

### 4. Testing on localhost

1. Make sure the backend is running on `http://localhost:8080`.
2. Make sure the frontend is running on `http://localhost:5173`.
3. Open the frontend in your browser.
4. Upload an audio file and verify the request is sent to `http://localhost:8080/api/transcribe`.
5. Check the transcription result in the UI or download the generated text file if needed.
