import { useContext, useState } from "react";
import { authenticate } from "./security/authApi";
import { useNavigate } from "react-router-dom";
import AuthContext from "./contexts/AuthContext";

function Login(){
    const [errors, setErrors] = useState([]);
    const [credentials, setCredentials] = useState({
        username: '',
        password: ''
    });

    const navigate = useNavigate();

    const auth = useContext(AuthContext);

    const handleChange = (evt) => {
        const nextCredentials = {...credentials};
        nextCredentials[evt.target.name] = evt.target.value;
        setCredentials(nextCredentials);
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        authenticate(credentials).then(user => {
            auth.onAuthenticated(user);
            navigate('/');
          })
          .catch(err => setErrors(err));
    }

    return (
        <>
            <div className="container-fluid">
                <h1>LOGIN</h1>
                {errors.length > 0 && (
                    <div className="alert alert-danger">
                        <p>{errors}</p>
                    </div>
                )}
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label htmlFor="username" className="form-label">Username:</label>
                        <input 
                        type="text"
                        className="form-control"
                        id="username"
                        name="username"
                        value={credentials.username}
                        onChange={handleChange} required/>
                    </div>
                    <div className="form-group">
                        <label htmlFor="password" className="form-label">Password:</label>
                        <input
                        type="password"
                        className="form-control"
                        id="password"
                        name="password"
                        value={credentials.password}
                        onChange={handleChange} required/>
                    </div>
                    <div>   
                        <button type="submit" className="btn btn-primary">Log In</button>
                        <button type="button" className="btn btn-cancel" onClick={() => {navigate('/')}}>Cancel</button>
                    </div>
                </form>
            </div>
        </>
    );
}

export default Login;