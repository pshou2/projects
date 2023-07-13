import { useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import AuthContext from "./contexts/AuthContext";

function EditDisplayProfile(){
    const [displayProfile, setDisplayProfile] = useState({});
    const [errors, setErrors] = useState([]);
    const navigate = useNavigate();
    const auth = useContext(AuthContext);

    const url = `http://18.217.22.92:8080/api/display_profile/username/${auth.user.username}`;
    const editurl = 'http://18.217.22.92:8080/api/display_profile/'

    useEffect(() => {
        fetch(url)
            .then(response => {
                if (response.status === 200){
                    return response.json();
                } else {
                    return Promise.reject(`Unexcpected status code: ${response.status}`);
                }
            })
            .then(data => setDisplayProfile(data))
            .catch(console.log);
    }, []);

    const handleChange = (event) => {
        const newDisplayProfile = {...displayProfile};
        newDisplayProfile[event.target.name] = event.target.value;
        setDisplayProfile(newDisplayProfile);
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        //expecting a 204 for happy path
        const jwtToken = localStorage.getItem('jwt_token');

        const init = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                'Authorization': `Bearer ${jwtToken}`
            },
            body: JSON.stringify(displayProfile)
        };

        fetch(`${editurl}${displayProfile.appUserId}`, init)
        .then(response => {
            if(response.status === 204) {
                return null;
            } else {
                return Promise.reject(`Unexpected status code: ${response.status}`);
            }
        })
        .then(data => {
            if(!data) {
                navigate(`/displayprofile/${auth.user.appUserId}`)
            } else {
                setErrors(data);
            }
        })
        .catch(console.log)
    }

    

    return (
        <>
            <section id="formContainer">
                {errors.length > 0 && (
                    <div className="alert alert-danger">
                        <p>The following errors were found:</p>
                        <ul>
                            {errors.map(error => 
                                <li key={error}>{error}</li>
                            )}
                        </ul>
                    </div>
                )}
                <h1>User: {displayProfile.username}</h1>
                <form id="form" onSubmit={handleSubmit}>
                    <fieldset className="form-group">
                        <label htmlFor="picture">Profile Picture URL: </label>
                        <input id="picture"
                        name="picture"
                        type="text"
                        className="form-control"
                        value={displayProfile.picture}
                        onChange={handleChange}/>
                    </fieldset>
                    <fieldset className="form-group">
                        <label htmlFor="bio">Bio: </label>
                        <textarea id="bio" 
                        name="bio"
                        value={displayProfile.bio}
                        onChange={handleChange}></textarea>
                    </fieldset>
                    <fieldset className="form-group">
                        <label htmlFor="twitter">Twitter Url: </label>
                        <input id="twitter"
                        name="twitter"
                        type="text"
                        className="form-control"
                        value={displayProfile.twitter}
                        onChange={handleChange}
                        />
                    </fieldset>
                    <fieldset className="form-group">
                        <label htmlFor="instagram">Instagram Url: </label>
                        <input id="instagram"
                        name="instagram"
                        type="text"
                        className="form-control"
                        value={displayProfile.instagram}
                        onChange={handleChange}
                        />
                    </fieldset>
                    <fieldset className="form-group">
                        <label htmlFor="tiktok">TikTok Url: </label>
                        <input id="tiktok"
                        name="tiktok"
                        type="text"
                        className="form-control"
                        value={displayProfile.tiktok}
                        onChange={handleChange}
                        />
                    </fieldset>
                    <div>
                        <button type="submit" className="btn btn-primary">Submit</button>
                        <button type="button" className="btn btn-cancel" onClick={() => {navigate(`/displayprofile/${auth.user.appUserId}`)}}>Cancel</button>
                    </div>
                </form>
            </section>
        </>
    );
};

export default EditDisplayProfile;