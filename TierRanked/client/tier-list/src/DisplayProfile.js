import { useContext, useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import AuthContext from "./contexts/AuthContext";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTwitter, faInstagram, faTiktok } from "@fortawesome/free-brands-svg-icons";
import { useParams } from 'react-router-dom';

function DisplayProfile(){
  const { displayProfileId } = useParams();
  const [displayProfile, setDisplayProfile] = useState({});
  const navigate = useNavigate();
  const auth = useContext(AuthContext);
  const [hotList, setHotList] = useState([]);
  const [tierList, setTierList] = useState([]);

  
  const url1 = `http://18.217.22.92:8080/api/tierlist/`;
  const url = `http://18.217.22.92:8080/api/display_profile/${displayProfileId}`;
  
  // const url = `http://18.217.22.92:8080/api/display_profile/username/${auth.user.username}`;

  useEffect(() => {
    fetch(url)
      .then(response => {
        if (response.status === 200){
          return response.json();
        } else {
          return Promise.reject(`Unexpected status code: ${response.status}`);
        }
      })
      .then(data => setDisplayProfile(data))
      .catch(console.log);
  }, [url]);

    /**
    Plan for Profile
        - Social media handles should be linked to by social media icons
        - Will need to use useparams to display the right profile
     */

       

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
      // Filter the tierlist based on appUserId
      const filteredTierList = data.filter((tierList) => tierList.appUserId === displayProfile.appUserId);

      // Sort the filtered tier list by upvotes in descending order
      const sortedTierList = filteredTierList.sort((a, b) => b.upvotes - a.upvotes);

      // Extract the top three lists
      const topThreeLists = sortedTierList.slice(0, 3);

      // Set the state variable
      setHotList(topThreeLists);
    })
    .catch(console.log);
}, [displayProfile.appUserId]);

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
      // Filter the tierlist based on appUserId
      const filteredTierList = data.filter((tier) => tier.appUserId === displayProfile.appUserId);

      // Sort the filtered tier list by date in descending order
      const sortedTierList = filteredTierList.sort((a, b) => new Date(b.datePosted) - new Date(a.datePosted));

      // Set the state variable
      setTierList(sortedTierList);
    })
    .catch(console.log);
}, [displayProfile.appUserId]);
       
        


  return (
    <div className="display-profile">
      <div className="profile">
        <div className="profile-picture">
          <img className="circular-profile-pic" src={displayProfile.picture} alt={`Profile Picture url: ${displayProfile.picture}`} />
        </div>
        <div className="profile-info">
          <div className="profile-name-bio">
            <h1>{displayProfile.username}</h1>
            <p>{displayProfile.bio}</p>
          </div>
          <div className="social-links">
            <ul>
              <li>
                <Link to={displayProfile.twitter}>
                  <FontAwesomeIcon icon={faTwitter} />
                  <span>{displayProfile.twitterHandle}</span>
                </Link>
              </li>
              <li>
                <Link to={displayProfile.instagram}>
                  <FontAwesomeIcon icon={faInstagram} />
                  <span>{displayProfile.instagramHandle}</span>
                </Link>
              </li>
              <li>
                <Link to={displayProfile.tiktok}>
                  <FontAwesomeIcon icon={faTiktok} />
                  <span>{displayProfile.tiktokHandle}</span>
                </Link>
              </li>
            </ul>
          </div>
          <button onClick={() => { navigate(`/editdisplayprofile/${displayProfileId}`) }}>Edit Profile</button>
        </div>
      </div>
      <div className="tier-lists">
        <div className="hot-lists">
          <h2>Hottest Lists</h2>
          <div className="list-container">
            {hotList.map((item) => (
              <div className="list-item" key={item.tierListId}>
                <Link to={`/tierlist/${item.tierListId}`}>{item.name}</Link>
                <p>{item.description}</p>
                <p>&#11014; {item.upvotes} &#11015; {item.downvotes}</p>
              </div>
            ))}
          </div>
        </div>
        <div className="all-lists">
          <h2>All Lists</h2>
          <div className="list-container">
            {tierList.map((item) => (
              <div className="list-item" key={item.tierListId}>
                <Link to={`/tierlist/${item.tierListId}`}>{item.name}</Link>
                <p>{item.description}</p>
                <p>&#11014; {item.upvotes} &#11015; {item.downvotes}</p>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );

}

export default DisplayProfile;