import { useEffect, useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';

const Dropdown = () => {
  const [selectedOption, setSelectedOption] = useState('');
  const [categories, setCategories] = useState([]);
  const navigate = useNavigate();
  const location = useLocation();

  const url = 'http://18.217.22.92:8080/api/category';

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
  }, []);

  useEffect(() => {
    setSelectedOption(''); // Reset the selected option when the location changes
  }, [location]);

  const handleOptionChange = (event) => {
    const selectedOption = event.target.value;
    setSelectedOption(selectedOption);
  
    if (selectedOption) {
      navigate(`/categories/${selectedOption}`);
    } 
  };

  return (
    <div className="custom-dropdown">
      <select value={selectedOption} onChange={handleOptionChange}>
        <option value="">Categories</option>
        {categories.map((category) => (
          <option key={category.categoryId} value={category.categoryId}>
            {category.name}
          </option>
        ))}
      </select>
    </div>
  );
};

export default Dropdown;
