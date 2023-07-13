import { Link } from "react-router-dom";

function NotFound() {
  return (
    <div className="not-found">
      <div className="row">
        <div className="col-lg-2"></div>
        <div className="col-lg-8">
          <div className="col-lg-12 text-404">
            <b>404</b>
          </div>
          <div className="col-lg-12 text">
            <b>PAGE NOT FOUND</b>
          </div>
          <div className="col-lg-12 text-btn">
            <Link to="/">
              <button name="home" className="btn btn-outline-primary">BACK TO HOME</button>
            </Link>
          </div>
        </div>
        <div className="col-lg-2"></div>
      </div>
    </div>
  );
}

export default NotFound;