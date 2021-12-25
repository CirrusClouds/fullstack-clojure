import React from 'react';
import logo from './logo.svg';
import './App.css';
import { makeStyles } from '@mui/styles';
import MyDrawer from './containers/drawers';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Bookmarks from './components/bookmarks';

const useStyles = makeStyles({
  page: {
    background: "#f9f9f9",
    display: "flex",
  },
});

function BaseRouter() {
  return (
    <Routes>
      <Route path="/bookmarks" element={<Bookmarks />} />
    </Routes>
  )
}

function App() {
  const classes = useStyles();
  
  return (
    <Router>
      <div className={classes.page}>
        <BaseRouter />
        <MyDrawer />
      </div>
    </Router>
  );
}

export default App
