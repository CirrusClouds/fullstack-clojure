import React from 'react';
import logo from '../logo.svg';
import { Drawer, Typography, List, ListItem, ListItemText } from '@mui/material';
import { makeStyles } from '@mui/styles';
import { Link } from 'react-router-dom/';
const useStyles = makeStyles({
  drawer: {},
})

function MyDrawer() {
  const classes = useStyles();

  const menuItems = [
    {
      text: "Home",
      link: "/"
    },
    {
      text: "Bookmarks",
      link: "/bookmarks",
    },
  ];

  return (
    <Drawer variant="permanent" anchor="right" className={classes.drawer}>
      <div>
        <img src={logo} alt="" />
        <Typography variant="h5">
          Jaime's Library
        </Typography>
        <List>
          {menuItems.map(item => (
            <ListItem>
              <Link to={item.link}>
                <ListItemText primary={item.text} />
              </Link>
            </ListItem>
          ))}
        </List>
      </div>
    </Drawer>
  )
};

export default MyDrawer;
