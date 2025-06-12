import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import { GoogleOAuthProvider } from '@react-oauth/google'

const googleClientId = '890688268052-oslb086a0802rl5p8dtkgad95jkr8cp4.apps.googleusercontent.com';

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <GoogleOAuthProvider clientId={googleClientId}>
      <App/>
    </GoogleOAuthProvider>
     
  </StrictMode>
)
