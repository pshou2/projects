import { useCallback, useEffect, useState } from "react";
import { BrowserRouter as Router, useParams } from "react-router-dom";
import { Routes, Route } from "react-router-dom";
import Home from "./Home";
import Navbar from "./Navbar";
import TierListForm from "./TierListForm";
import Categories from "./Categories";
import SignUp from "./SignUp";
import Login from "./LogIn";
import NotFound from "./NotFound";
import DisplayProfile from "./DisplayProfile";
import AuthContext from "./contexts/AuthContext";
import { refreshToken, signOut } from "./security/authApi";
import EditDisplayProfile from "./EditDisplayProfile";

import CreateTierListContainer from "./CreateTierListContainer";
import CreateTierList from "./CreateTierList";


const EMPTY_USER = {
  username: '',
  roles: []
};

const WAIT_TIME = 1000 * 60 * 14;


function App() {
  const [user, setUser] = useState(EMPTY_USER);

  const refreshUser = useCallback(() => {
    refreshToken()
      .then(existingUser => {
        setUser(existingUser);
        setTimeout(refreshUser, WAIT_TIME);
      })
      .catch(err => {
        console.log(err);
      });
  }, []);

  useEffect(() => {
    refreshUser();
  }, [refreshUser]);

  const auth = {
    user: user,
    isLoggedIn() {
      return !!user.username;
    },
    hasRole(role) {
      return user.roles.includes(role);
    },
    onAuthenticated(user) {
      setUser(user);
      setTimeout(refreshUser, WAIT_TIME);
    },
    signOut() {
      setUser(EMPTY_USER);
      signOut();
    }
  };

  const { displayProfileId } = useParams();

  return (
    <AuthContext.Provider value={auth}>
      <Router>
        <Navbar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/tierlistform" element={<TierListForm />} />
          <Route path="/categories/:categoryId" element={<Categories />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/login" element={<Login />} />
          <Route path="/displayprofile/:displayProfileId" element={<DisplayProfile displayProfileId={displayProfileId} />} />
          <Route path="/editdisplayprofile/:displayProfileId" element={<EditDisplayProfile />} />
          <Route path="/tierlist/:tierListId" element={<CreateTierList />} />
          <Route path="*" element={<NotFound />} />
        </Routes>
      </Router>
    </AuthContext.Provider>
  );
}

export default App;
