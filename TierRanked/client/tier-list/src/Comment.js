import React, { useEffect, useState, useContext } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import AuthContext from './contexts/AuthContext';

const Comment = () => {
  const { tierListId } = useParams();
  const navigate = useNavigate();
  const auth = useContext(AuthContext);
  const jwtToken = localStorage.getItem('jwt_token');

  const [comments, setComments] = useState(null);
  const [comment, setComment] = useState('');
  const [tierList, setTierList] = useState([]);
  const [errors, setErrors] = useState([]);
  const [users, setUsers] = useState({});
  const [characterCount, setCharacterCount] = useState(0);

  const url = `http://18.217.22.92:8080/api/tierlist/${tierListId}`;
  const url1 = 'http://18.217.22.92:8080/api/comment';

  

  const maxCharacterLimit = 1000;

  useEffect(() => {
    fetch(url)
      .then((response) => {
        if (response.status === 200) {
          return response.json();
        } else {
          return Promise.reject(`Unexpected status code: ${response.status}`);
        }
      })
      .then((data) => setTierList(data))
      .catch(console.log);
  }, []);

  useEffect(() => {
    fetch(url1)
      .then((response) => {
        if (response.status === 200) {
          return response.json();
        } else {
          return Promise.reject(`Unexpected status code: ${response.status}`);
        }
      })
      .then((data) => {
        // Filter the comments based on tier_list_id
        const filteredComments = data.filter((comment) => comment.tierListId == tierListId);
        setComments(filteredComments);
      })
      .catch(console.log);
  }, [tierListId, comments]);
  
  useEffect(() => {
    fetch('http://18.217.22.92:8080/api/user')
      .then((response) => {
        if (response.status === 200) {
          return response.json();
        } else {
          return Promise.reject(`Unexpected status code: ${response.status}`);
        }
      })
      .then((data) => {
        const userMap = {};
        data.forEach((user) => {
          userMap[user.appUserId] = user.username;
        });
        setUsers(userMap);
      })
      .catch(console.log);
  }, []);

  if (!comments || !users || !tierList) {
    return null;
  }

  const handleCommentChange = (event) => {
    const inputValue = event.target.value;
    setComment(inputValue);
    setCharacterCount(inputValue.length);
  };

  const handleDeleteConfirmation = (commentId) => {
    const confirmDelete = window.confirm(
      'Are you sure you want to delete this comment?'
    );
    if (confirmDelete) {
      handleDelete(commentId);
    } else {
      const deleteButton = document.activeElement;
      deleteButton.blur(); // remove focus from the delete button
    }
  };

  // only admins will be able to delete
  const handleDelete = (commentId) => {
    if (!auth.user || !auth.user.roles.includes('ADMIN')) {
      // user is not an admin, cannot delete the comment
      return;
    }

    const deleteUrl = `${url1}/${commentId}`;

    const init = {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${jwtToken}`,
      },
    };

    fetch(deleteUrl, init)
      .then((response) => {
        if (response.status === 204) {
          // update the comment list after successful deletion
          setComments(comments.filter((comment) => comment.commentId !== commentId));
        } else {
          return Promise.reject(`Unexpected status code: ${response.status}`);
        }
      })
      .catch(console.log);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!auth.user) {
      // user is not logged in, redirect to login page
      navigate('/login');
      return;
    }

    const appUserId = auth.user.appUserId;

    // create a new comment object
    const newComment = {
      commentId: 0,
      comment: comment,
      timestamp: new Date().toISOString(),
      appUserId: appUserId,
      tierListId: tierList.tierListId,
    };

    // clear the comment input field
    setComment('');

    const init = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${jwtToken}`,
      },
      body: JSON.stringify(newComment),
    };

    fetch(url1, init)
      .then((response) => {
        if (response.status === 201 || response.status === 400) {
          return response.json();
        } else {
          return Promise.reject(`Unexpected status code: ${response.status}`);
        }
      })
      .then((data) => {
        if (data.commentId) {
          // update the comments state with the new comment
          setComments([...comments, data]);

        } else {
          setErrors(data);
        }
      })
      .catch(console.log);
  };

  const isLoggedIn = () => {
    return !!localStorage.getItem('jwt_token');
  };

  const formatDate = (timestamp) => {
    const dateOptions = {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
    };

    const timeOptions = {
      hour: 'numeric',
      minute: 'numeric',
    };

    const date = new Date(timestamp);
    const formattedDate = date.toLocaleDateString(undefined, dateOptions);
    const formattedTime = date.toLocaleTimeString(undefined, timeOptions);

    return `${formattedDate} - ${formattedTime}`;
  };

  return (
    <div className="comments-section">
      <h2>Comments</h2>
      {errors.length > 0 && (
        <div className="alert alert-danger">
          <p>The following errors were found:</p>
          <ul>
            {errors.map((error) => (
              <li key={error}>{error}</li>
            ))}
          </ul>
        </div>
      )}
      <div className="comments-list">
        {comments.map((comment) => (
          <div key={comment.commentId} className="comment card mb-3">
            <h5 className="comment-username">{users[comment.appUserId]}</h5>
            <p className="comment-text">{comment.comment}</p>
            <p className="comment-timestamp">{formatDate(comment.timestamp)}</p>
            <button
              type="button"
              className="btn btn-danger btn-lg delete-button"
              onClick={() => handleDeleteConfirmation(comment.commentId)}
              style={{
                display: auth.user && auth.user.roles.includes('ADMIN') ? 'inline-block' : 'none',
              }}
            >
              <span className="delete-button-text">Delete</span>
            </button>
          </div>
        ))}
      </div>
      {isLoggedIn() && (
        <form onSubmit={handleSubmit} className="comment-form">
          <div className="form-group">
            <textarea
              value={comment}
              onChange={handleCommentChange}
              placeholder="Write a comment..."
              className="form-control custom-textarea"
            ></textarea>
            <div className="character-count">
              {characterCount}/{maxCharacterLimit}
            </div>
          </div>
          <button type="submit" className="btn btn-primary btn-lg" disabled={!comment}>
            Submit
          </button>
        </form>
      )}
    </div>
  );
};

export default Comment;