import React from 'react';
import axios from 'axios';
import { List, ListItem, ListItemText } from '@mui/material';

interface BookmarkProps {
  name: string,
  url: string,
  description: string,
};

function Bookmarks() {
  var [bookmarks, setBookmarks]: [Array<BookmarkProps>, (p: Array<BookmarkProps>) => void] =
    React.useState(
      [{name: "", url: "", description: ""}]
  );

  React.useEffect(() => {
    axios.get(`http://localhost:4000/api/bookmarks/`).then(res => {
      setBookmarks(res.data);
    })
  }, []);

  return (
    <div>
      <List>
        {bookmarks.map(bookmark => (
          <ListItem>
            {bookmark.name} {bookmark.url} {bookmark.description}
          </ListItem>
        ))}
      </List>
    </div>
  )
};

export default Bookmarks;
