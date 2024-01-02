import React, { useState, useEffect } from "react";
import GroupCard from "../pages/GroupCard.js";
import { useLocation, Link, useNavigate } from "react-router-dom";
import SuccessModal from "./SuccessModal.js";
import "../styles/createGroup.css";
import Navbar from "./NavBar.js";
import Footer from "../pages/Footer";

const CreateGroup = () => {
  const [groupName, setGroupName] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [groups, setGroups] = useState([]);
  const location = useLocation();
  const userEmail = location.state ? location.state.email : null;
  const [showSuccessModal, setShowSuccessModal] = useState(false);
  const navigate = useNavigate();
  const [filteredGroups, setFilteredGroups] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  useEffect(() => {
    // Check for user email in local storage
    const storedEmail = localStorage.getItem('email');

    if (!storedEmail) {
      // If email is not present, navigate to the login page
      navigate('/login');
    }
  }, [navigate]);
  const handleInputChange = (e) => {
    setGroupName(e.target.value);
  };

  const handleSearchChange = (e) => {
    setSearchTerm(e.target.value);
    filterGroups(e.target.value);
  };

  const filterGroups = (term) => {
    const filtered = groups.filter((group) =>
      group.name.toLowerCase().includes(term.toLowerCase())
    );
    setFilteredGroups(filtered);
  };

  const handleLogout = () => {
    // Clear local storage and navigate to the logout page
    localStorage.removeItem('email');
    navigate('/');
  };

  const addUserToGroup = async (groupId) => {
    try {
      const response = await fetch(
        `http://localhost:8080/usergroup/addUserToGroup?userEmail=${userEmail}&groupId=${groupId}`,
        {
          method: "POST", 
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      if (response.ok) {
        // Handle success if needed
        console.log("User added to the group successfully");
        setSuccessMessage("User added to the group successfully");
        setShowSuccessModal(true);
      } else {
        // Handle error if needed
        console.error("Error adding user to group:", response.statusText);
      }
    } catch (error) {
      console.error("An error occurred while adding user to group:", error);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch("http://localhost:8080/groups", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ name: groupName }),
      });

      if (response.ok) {
        const responseData = {
          name: groupName,
          message: "Group created successfully",
        };
        setSuccessMessage(responseData.message);
        setErrorMessage("");
        setGroups([...groups, responseData]);
        setGroupName(""); // Clear input field after successful submission
        window.location.reload();
      } else {
        const errorData = await response.json();
        setErrorMessage(
          errorData.error || "An error occurred while creating the group."
        );
        setSuccessMessage("");
      }
    } catch (error) {
      console.error("An error occurred while creating the group:", error);
      setErrorMessage("An error occurred while creating the group.");
      setSuccessMessage("");
    }
  };

  useEffect(() => {
    // Fetch available groups from the database
    const fetchGroups = async () => {
      try {
        const response = await fetch("http://localhost:8080/groups");

        if (response.ok) {
          const groupsData = await response.json();
          setGroups(groupsData);
        } else {
          console.error("Error fetching groups:", response.statusText);
        }
      } catch (error) {
        console.error("An error occurred while fetching groups:", error);
      }
    };

    fetchGroups();
  }, []);

  return (
    <>
    <Navbar/>
    <div className="container">
      <h2>Create a Group</h2>
      <form className="group-form" onSubmit={handleSubmit}>
        <label>
          Group Name:
          <input
            type="text"
            value={groupName}
            onChange={handleInputChange}
            required
          />
        </label>
        <button type="submit">Create Group</button>
      </form>
       {/* Add Search Bar */}
       <div className="search-bar">
          <label>
            Search Group:
            <input
              type="text"
              value={searchTerm}
              onChange={handleSearchChange}
            />
          </label>
        </div>

        {/* Display Filtered Groups */}
        <div className="group-container">
          <h2>Groups</h2>
          <div className="group-cards">
            {(searchTerm ? filteredGroups : groups).map((group, index) => (
              <GroupCard
                key={index}
                groupId={group.id}
                groupName={group.name}
                addUserToGroup={addUserToGroup}
                className="group-card"
              />
            ))}
          </div>
        </div>

      {errorMessage && <p className="error-message">{errorMessage}</p>}
      {successMessage && (
        <div className="success-message">
           <h3>Group Created Successfully!</h3>
          <p>Group Name: {successMessage}</p>
        </div>
      )}
      <SuccessModal
        show={showSuccessModal}
        onClose={() => {
          setShowSuccessModal(false);
          setSuccessMessage("");
        }}
        message={successMessage}
      />
      <div className="logout-container">
      <button onClick={handleLogout} className="logout-button">
          Logout
        </button>
      <button onClick={() => navigate('/calculator')} className="logout-button">Calculator</button>
      </div>
    </div>
    <Footer/>
    </>
  );
};

export default CreateGroup;
