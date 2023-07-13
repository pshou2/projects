import React from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faFacebook, faTwitter, faInstagram } from '@fortawesome/free-brands-svg-icons';

const ContactUs = () => {
  return (
    <div className="contact-us">
      <h2>Contact Us</h2>
      <p>tierranked@gmail.com</p>
      <div className="social-links">
        <a href="https://www.facebook.com/tierranked" target="_blank" rel="noopener noreferrer">
          <FontAwesomeIcon icon={faFacebook} />
          <span className="handle">tierranked</span>
        </a>
        <a href="https://www.twitter.com/tierranked" target="_blank" rel="noopener noreferrer">
          <FontAwesomeIcon icon={faTwitter} />
          <span className="handle">@tierranked</span>
        </a>
        <a href="https://www.instagram.com/tierranked" target="_blank" rel="noopener noreferrer">
          <FontAwesomeIcon icon={faInstagram} />
          <span className="handle">@tierranked</span>
        </a>
      </div>
    </div>
  );
}

export default ContactUs;
