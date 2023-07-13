import { useEffect, useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useParams } from 'react-router-dom';




const Categories = () => {
  const { categoryId } = useParams();
  const [categories, setCategories] = useState(null);
  const [tiers, setTiers] = useState(null);
  const navigate = useNavigate();

  const url = `http://18.217.22.92:8080/api/category/${categoryId}`;

  const tierUrl =`http://18.217.22.92:8080/api/tierlist/category/${categoryId}`;

  //const usernameurl = "http://18.217.22.92:8080/api/app_user/${app_user_id}";

  useEffect(() => {
    fetch(url)
      .then((response) => {
        if (response.status === 200) {
          return response.json();
        } else {
          return Promise.reject(`Unexpected status code: ${response.status}`);
        }
      })
      .then((data) => setCategories(data))
      .catch(console.log);
  }, [categoryId]);
  

  useEffect(() => {
    fetch(tierUrl)
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
        setTiers(trendingTiers);
      })
      .catch(console.log);
  }, [tierUrl]);

  
  if (!categories) {
    return console.log(categories);
  }

  if (!tiers) {
    return console.log(tiers);
  }

  return (
    <div className="top-tier-lists">
      <h1>{categories.name}</h1>
      <h2>Hottest Tiers:</h2>
      <ul>
        {tiers.map((tier) => (
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
    </div>
  );  
};

export default Categories;