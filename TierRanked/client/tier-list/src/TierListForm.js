import CreateTierListContainer from "./CreateTierListContainer";
import { useContext, useEffect, useState } from "react";
import initialData from "./initialData";
import { useNavigate, useParams } from 'react-router-dom';
import AuthContext from "./contexts/AuthContext";

function TierListForm() {
  const [appState, setAppState] = useState(initialData);
  const [genres, setGenres] = useState({});
  const [itemCount, setItemCount] = useState(0);
  const [listName, setListName] = useState("");
  const [listDescription, setListDescription] = useState("");
  const [categories, setCategories] = useState(null);
  const [errors, setErrors] = useState([]);
  const [users, setUsers] = useState({});
  const navigate = useNavigate();
  const auth = useContext(AuthContext)

  const url1 = `http://18.217.22.92:8080/api/category`;


  useEffect(() => {
    fetch(url1)
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
    addMultipleItems(0);
  }, []);

  const addMultipleItems = (amount) => {
    let newItems = {};
    let newItemRow = [];
    let currentItemCount = itemCount;
    setItemCount(itemCount + amount);
    for (let i = 0; i < amount; i++) {
      const itemName = "item-" + currentItemCount;
      console.log("itemName :", itemName);
      const newItem = {
        [itemName]: {
          id: itemName,
          altText: itemName,
          desc: itemName,
        },
      };
      const oldItemTrayRow = appState.newDraft.rows["row-tray"].itemIds;
      newItemRow = [...newItemRow, itemName];
      newItems = { ...newItems, ...newItem };
      currentItemCount++;
    }
    setAppState((prevState) => ({
      ...prevState,
      newDraft: {
        ...prevState.newDraft,
        items: { ...newItems, ...prevState.newDraft.items },
        rows: {
          ...prevState.newDraft.rows,
          "row-tray": {
            ...prevState.newDraft.rows["row-tray"],
            itemIds: [
              ...prevState.newDraft.rows["row-tray"].itemIds,
              ...newItemRow,
            ],
          },
        },
      },
    }));
  };

  const addItem = () => {

    const imageUrl = prompt("Enter the image URL for the item:");
    const itemName = imageUrl;
    const newItem = {
      [itemName]: {
        id: itemName,
        altText: itemName,
        desc: imageUrl,
        imageUrl: imageUrl,
      },
    };
    const oldItemTrayRow = appState.newDraft.rows["row-tray"].itemIds;
    const newItems = { ...appState.newDraft.items, ...newItem };
    setAppState((prevState) => ({
      ...prevState,
      newDraft: {
        ...prevState.newDraft,
        items: newItems,
        rows: {
          ...prevState.newDraft.rows,
          "row-tray": {
            ...prevState.newDraft.rows["row-tray"],
            itemIds: [...oldItemTrayRow, itemName],
          },
        },
      },
    }));
    setItemCount(itemCount + 1);
  };

  const handleListNameChange = (event) => {
    setListName(event.target.value);
  };

  const handleListDescriptionChange = (event) => {
    setListDescription(event.target.value);
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    const url = `http://18.217.22.92:8080/api/tierlist`;

    const listName = event.target.elements.listName.value;
    const listDescription = event.target.elements.listDescription.value;
    const selectedCategoryId = event.target.elements.category.value;
    const sTier = appState.newDraft.rows["row-1"].itemIds.length ? JSON.stringify(appState.newDraft.rows["row-1"].itemIds) : null;
    const aTier = appState.newDraft.rows["row-2"].itemIds.length ? JSON.stringify(appState.newDraft.rows["row-2"].itemIds) : null;
    const bTier = appState.newDraft.rows["row-3"].itemIds.length ? JSON.stringify(appState.newDraft.rows["row-3"].itemIds) : null;
    const cTier = appState.newDraft.rows["row-4"].itemIds.length ? JSON.stringify(appState.newDraft.rows["row-4"].itemIds) : null;
    const dTier = appState.newDraft.rows["row-5"].itemIds.length ? JSON.stringify(appState.newDraft.rows["row-5"].itemIds) : null;
    const eTier = appState.newDraft.rows["row-6"].itemIds.length ? JSON.stringify(appState.newDraft.rows["row-6"].itemIds) : null;
    const fTier = appState.newDraft.rows["row-7"].itemIds.length ? JSON.stringify(appState.newDraft.rows["row-7"].itemIds) : null;

    const appUserId = auth.user.appUserId;

    console.log('List Name:', listName);
    console.log('List Description:', listDescription);
    console.log('Selected Category ID:', selectedCategoryId);
    console.log('sTier:', sTier);
    console.log('aTier:', aTier);
    console.log('bTier:', bTier);
    console.log('cTier:', cTier);
    console.log('dTier:', dTier);
    console.log('eTier:', eTier);
    console.log('fTier:', fTier);


    const tierList = {
      name: listName,
      description: listDescription,
      timestamp: new Date().toISOString(),
      s_Tier: sTier,
      a_Tier: aTier,
      b_Tier: bTier,
      c_Tier: cTier,
      d_Tier: dTier,
      e_Tier: eTier,
      f_Tier: fTier,
      upvotes: 0,
      downvotes: 0,
      appUserId: appUserId, // will have to update to be logged in and have appUserId
      categoryId: selectedCategoryId,
    };


    console.log('Tier List:', tierList);

    const jwtToken = localStorage.getItem('jwt_token');

    const init = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
        'Authorization': `Bearer ${jwtToken}`
      },
      body: JSON.stringify(tierList)
    };

    fetch(url, init)
      .then(response => {
        if (response.status === 201 || response.status === 400) {
          return response.json();
        } else {
          return Promise.reject(`Unexpected status code: ${response.status}`);
        }
      })
      .then(data => {
        if (data.tierListId) {
          navigate(`/tierlist/${data.tierListId}`);
        } else {
          setErrors(data);
        }
      })
      .catch(console.log);
  };



  if (!categories) {
    return console.log(categories);
  }

  return (
    <div className="container">
      <h1>Create a Tier List</h1>
      <div>
        <form onSubmit={handleSubmit}>
          <label htmlFor="listName">List Name:</label>
          <input type="text" id="listName" name="listName" onChange={handleListNameChange} />

          <label htmlFor="listDescription">List Description:</label>
          <textarea id="listDescription" name="listDescription" onChange={handleListDescriptionChange} />
          <br />

          <label>Category:</label>
          <div className="formCategory">
            {categories.map((category) => (
              <div key={category.id}>
                <input type="radio" id={`category_${category.categoryId}`} name="category" value={category.categoryId} />
                <label htmlFor={`category_${category.categoryId}`}>{category.name}</label>
              </div>
            ))}
          </div>

          <button type="button" onClick={addItem}>Add An Item</button>

          <br />
          <button type="submit">Submit</button>
        </form>
        <CreateTierListContainer appState={appState} setAppState={setAppState} />
      </div>
    </div>
  );


}

export default TierListForm;