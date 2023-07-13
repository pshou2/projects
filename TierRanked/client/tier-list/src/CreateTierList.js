import CreateTierListContainer from "./CreateTierListContainer";
import { useContext,useEffect, useState } from "react";
import initialData from "./initialData";
import initialNewTopicData from "./initialNewTopicData";
import TierRowTray from "./TierRowTray";
import { useNavigate, Link } from 'react-router-dom';
import { useParams } from 'react-router-dom';
import styled from "styled-components";
import TierRow from "./TierRow";
import Comment from './Comment';
import { authenticate } from "./security/authApi";
import AuthContext from "./contexts/AuthContext";


const TierRowContainer = styled.div`
  background-color: #1a1a1a;
  min-width: 100px;
  margin: 10px 0;
  display: grid;
  grid-template-columns: 60px 1fr;
  align-items: center;
`;

const FullScreenButton = styled.button`
  position: absolute;
  bottom: 0;
  right: 0;
  width: 30px;
  height: 30px;
  background-color: #ffffff;
  display: grid;
  place-items: center;
  border-radius: 2px;
  outline: none;
`;

const TierListContainer = styled.div`
  overflow: auto;
  background-color: #000000;
  min-width: 100%;
  min-height: 500px;
  position: relative;
`;

const DeleteButton = styled.button`
  display: ${({ isAdmin }) => (isAdmin ? "inline-block" : "none")};
`;

function CreateTierList() {
    
    const { tierListId } = useParams({});
    const [tierList, setTierList] = useState([]);
    const navigate = useNavigate();
    const [comments, setComments] = useState(null);
    const [comment, setComment] = useState('');
    const [errors, setErrors] = useState([]);
    const [users, setUsers] = useState({});
    const auth = useContext(AuthContext);
    const [userVotes, setUserVotes] = useState({ upvoted: false, downvoted: false });
    const [username,setUsername] = useState([]);

    const url = `http://18.217.22.92:8080/api/tierlist/${tierListId}`;
    const url1 = 'http://18.217.22.92:8080/api/comment';

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
      }, [tierListId]);
      
    
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

      
    
      const handleVoteUpdate = (upvotes, downvotes) => {
        const url = `http://18.217.22.92:8080/api/tierlist/${tierListId}`;
        const updatedTierList = {
          ...tierList,
          upvotes,
          downvotes,
        };
      
        const jwtToken = localStorage.getItem('jwt_token');
        fetch(url, {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${jwtToken}`,
          },
          body: JSON.stringify(updatedTierList),
        })
          .then((response) => {
            if (response.status === 204) {
              // Update the tierList state with the new vote counts
              setTierList(updatedTierList);
            } else {
              return Promise.reject(`Unexpected status code: ${response.status}`);
            }
          })
          .catch(console.log);
      };
      
      const handleUpvote = () => {
        if (!userVotes.upvoted) {
          // Perform the upvote action
          setUserVotes({ upvoted: true, downvoted: true });
          handleVoteUpdate(tierList.upvotes + 1, tierList.downvotes);
        }
      };
      
      const handleDownvote = () => {
        if (!userVotes.downvoted) {
          // Perform the downvote action
          setUserVotes({ upvoted: true, downvoted: true });
          handleVoteUpdate(tierList.upvotes, tierList.downvotes + 1);
        }
      };

      const handleDeleteTierList = () => {
        if (!auth.user || !auth.user.roles.includes('ADMIN')) {
          // user is not an admin, cannot delete the tier list
          return;
        }
      
        const confirmDelete = window.confirm('Are you sure you want to delete this tier list?');
      
        if (confirmDelete) {
          const deleteUrl = `${url}`;
      
          const jwtToken = localStorage.getItem('jwt_token');
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
                // navigate to home after successful deletion
                navigate('/');
              } else {
                return Promise.reject(`Unexpected status code: ${response.status}`);
              }
            })
            .catch(console.log);
        } else {
          const deleteButton = document.activeElement;
          if (deleteButton) {
            deleteButton.blur(); // remove focus from the delete button
          }
        }
      };
      
      

      


    if (!tierList) {
        return null;
      }
  
    return (
        <>
          <div className="tierlist-list">
            {auth.user && auth.user.roles.includes('ADMIN') && (
            <DeleteButton isAdmin className="btn btn-danger btn-lg" onClick={handleDeleteTierList}>
              Delete Tier List
            </DeleteButton>
            )}
            <h1>{tierList.name}</h1>
            <p>"{tierList.description}"</p>
            <Link className="profile-link" to={`http://tierlist-front.s3-website.us-east-2.amazonaws.com/displayprofile/${tierList.appUserId}`}>{users[tierList.appUserId]}</Link>
          <TierListContainer id="create-tier-list-container">
            <TierRow row={1} letter={"S"} color={"#ff7f7f"} items={tierList.s_Tier?.slice(1, -1).split(', ')} itemMap={0} userVotes={userVotes} handleUpvote={handleUpvote} handleDownvote={handleDownvote}/>
            <TierRow row={1} letter={"A"} color={"#ffbf7f"} items={tierList.a_Tier?.slice(1, -1).split(', ')} itemMap={0} userVotes={userVotes} handleUpvote={handleUpvote} handleDownvote={handleDownvote}/>
            <TierRow row={1} letter={"B"} color={"#ffff7f"} items={tierList.b_Tier?.slice(1, -1).split(', ')} itemMap={0} userVotes={userVotes} handleUpvote={handleUpvote} handleDownvote={handleDownvote}/>
            <TierRow row={1} letter={"C"} color={"#7fff7f"} items={tierList.c_Tier?.slice(1, -1).split(', ')} itemMap={0} userVotes={userVotes} handleUpvote={handleUpvote} handleDownvote={handleDownvote}/>
            <TierRow row={1} letter={"D"} color={"#7fbfff"} items={tierList.d_Tier?.slice(1, -1).split(', ')} itemMap={0} userVotes={userVotes} handleUpvote={handleUpvote} handleDownvote={handleDownvote}/>
            <TierRow row={1} letter={"E"} color={"#7f7fff"} items={tierList.e_Tier?.slice(1, -1).split(', ')} itemMap={0} userVotes={userVotes} handleUpvote={handleUpvote} handleDownvote={handleDownvote}/>
            <TierRow row={1} letter={"F"} color={"#ff7fff"} items={tierList.f_Tier?.slice(1, -1).split(', ')} itemMap={0} userVotes={userVotes} handleUpvote={handleUpvote} handleDownvote={handleDownvote}/>
          </TierListContainer>
          <div className="vote-button">
            <button className="arrow-button" onClick={handleUpvote}>
              <span className="arrow-icon">&#11014;</span>
            </button>
            <span className="vote-count">{tierList.upvotes}</span>
            <button className="arrow-button" onClick={handleDownvote}>
              <span className="arrow-icon">&#11015;</span>
            </button>
            <span className="vote-count">{tierList.downvotes}</span>
          </div>
        </div>
        <Comment />
        </>
      );
  }
  
  export default CreateTierList;
  