import React from 'react';
import Comment from './Comment';
import ContactUs from './ContactUs';
import { useNavigate, Link } from 'react-router-dom';
import { useContext,useEffect, useState } from "react";

function Home() {
  const [tierList, setTierList] = useState([]);
  const [errors, setErrors] = useState([]);
  const navigate = useNavigate();

  const url = `http://18.217.22.92:8080/api/tierlist/`;
  

  useEffect(() => {
    fetch(url)
      .then((response) => {
        if (response.status === 200) {
          return response.json();
        } else {
          return Promise.reject(`Unexpected status code: ${response.status}`);
        }
      })
      .then((data) => {

        const sortedTiers = data.sort((a, b) => b.upvotes - a.upvotes);
        const trendingTiers = sortedTiers.slice(0, 10);
        setTierList(trendingTiers);
      })
      .catch(console.log);
  }, []);

  return (
    <div className="home">
      <header>
        <div>
          <h1>TierRanked</h1>
        </div>
      </header>
      <section className="top-tier-lists">
        <h2>Most Popular Tier Lists</h2>
        <ul>
          {tierList.map((tier) => (
            <Link to={`/tierlist/${tier.tierListId}`} className="tier-link" key={tier.id}>
              <li>
                <span className="upvotes">&#11014; {tier.upvotes}</span>
                <div className="link-description-container">
                  <p className="link">{tier.name}</p>
                  <p className="description">"{tier.description}"</p>
                </div>
              </li>
            </Link>
          ))}
        </ul>
      </section>
      <hr width="100%" />
      <footer className="footer">
        <ContactUs />
        <div className="copyright">
          <p>&copy; 2023 copyright</p>
        </div>
      </footer>
    </div>
  );
}

export default Home;