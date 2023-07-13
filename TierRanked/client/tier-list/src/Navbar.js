import React, { useContext } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import Dropdown from './Dropdown';
import AuthContext from './contexts/AuthContext';

const Navbar = ({ selectedCategory, handleCategoryChange }) => {
  const auth = useContext(AuthContext);
  const navigate = useNavigate();

  const handleSignOut = () => {
    auth.signOut();
    navigate('/');
  };

  const handleChange = (event) => {
    const category = event.target.value;
    handleCategoryChange(category);
  };

  return (
    <nav>
      <Link to="/">Home</Link>

      {auth.isLoggedIn() && <Link to="/tierlistform">Create a Tier List</Link>}

      <Dropdown value={selectedCategory} onChange={handleChange} />

      {auth.isLoggedIn() && <Link to={`/displayprofile/${auth.user.appUserId}`}>Display Profile</Link>}

      {auth.isLoggedIn() ? (
        <div>
          <span>Signed in as {auth.user.username}</span>
          <button type="button" className="btn btn-secondary" onClick={handleSignOut}>
            Sign out
          </button>
        </div>
      ) : (
        <>
          <Link to="/signup">Sign Up</Link>
          <Link to="/login">Log In</Link>
        </>
      )}
    </nav>
  );
};

export default Navbar;
