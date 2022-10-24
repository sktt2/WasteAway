import { initializeApp } from "firebase/app";
import { getStorage } from "firebase/storage";

// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
const firebaseConfig = {
    apiKey: "AIzaSyDfbRvMVbNTN4T_BDT1Wm4rsHTGpRgBCyg",
    authDomain: "wasteaway-d8e06.firebaseapp.com",
    projectId: "wasteaway-d8e06",
    storageBucket: "wasteaway-d8e06.appspot.com",
    messagingSenderId: "262347541191",
    appId: "1:262347541191:web:22eb1f3541ccde60ff8ce2"
  };
  
  // Initialize Firebase
  const app = initializeApp(firebaseConfig);


// Initialize Cloud Storage and get a reference to the service
const storage = getStorage(app);
export default storage;
