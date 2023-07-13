import { useState } from "react";
import { createAccount } from "./security/authApi";
import { useNavigate } from "react-router-dom";

const DEFAULT_DISPLAY_PROFILE = {
    picture: '',
    bio: '',
    username: '',
    appUserId: ''
};

function SignUp(){
    //create account and create a profile
    const [credentials, setCredentials] = useState({
        username: '',
        password: ''
    });
    const [errors, setErrors] = useState([]);
    const [displayProfile, setDisplayProfile] = useState(DEFAULT_DISPLAY_PROFILE);
    const navigate = useNavigate();

    const url = 'http://18.217.22.92:8080/api/display_profile';

    const addDisplayProfile = (newDisplayProfile) => {
        const init = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newDisplayProfile)
        };

        fetch(url, init)
        .then(response => {
            if (response.status === 201 || response.status === 400){
                return response.json();
            } else {
                return Promise.reject(`Unexpected status code: ${response.status}`);
            }
        })
        .then(data=>{
            if(data.displayProfileId){
                navigate('/');
            } else {
                setErrors(data);
            }
        })
    };

    //handle methods
    const handleSubmit = (event) => {
        event.preventDefault();
        createAccount(credentials)
            .then(appUserId => {
                const newDisplayProfile = {...displayProfile};
                newDisplayProfile.username = credentials.username;
                newDisplayProfile.appUserId = appUserId;
                setDisplayProfile(newDisplayProfile);
                addDisplayProfile(newDisplayProfile);
            })
            .catch(err => setErrors(err));
    };

    const handleChange = (evt) => {
        const nextCredentials = {...credentials};
        nextCredentials[evt.target.name] = evt.target.value;
        setCredentials(nextCredentials);
    };

    return (
        <>
            <div className="container-fluid">
                <h1>SIGN UP</h1>
                {errors.length > 0 && (
                    <div className="alert alert-danger">
                        <p>{errors}</p>
                    </div>
                )}
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label htmlFor="username" className="form-label">Enter a Username:</label>
                        <input 
                        type="text"
                        className="form-control"
                        id="username"
                        name="username"
                        placeholder="user@name"
                        value={credentials.username}
                        onChange={handleChange}/>
                    </div>
                    <div className="form-group">
                        <label htmlFor="password" className="form-label">Enter a Password:</label>
                        <input
                        type="password"
                        className="form-control"
                        id="password"
                        name="password"
                        placeholder="P@ssw0rd!"
                        value={credentials.password}
                        onChange={handleChange}/>
                    </div>
                    <button type="submit" className="btn btn-primary">Sign Up</button>
                    <button type="button" className="btn btn-cancel" onClick={() => {navigate('/')}}>Cancel</button>
                </form>
            </div>
        </>
    );
}

export default SignUp;