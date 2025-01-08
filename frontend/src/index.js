import React from 'react';
import ReactDOM from 'react-dom/client';
import './css/index.css';
import './css/login.css';
import './css/register.css';
import './css/home.css';
import './css/creation.css';
import './css/link.css';
import './css/update.css';
import './css/delete.css';
import App from './App';
import { UserProvider } from './context/UserContext';
import { DataProvider } from './context/DataContext';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <UserProvider>
      <DataProvider>
        <App />
      </DataProvider>
    </UserProvider>
  </React.StrictMode>
);
