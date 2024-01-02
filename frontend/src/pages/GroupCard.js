import React, { useState, useEffect, useCallback } from "react";
import { Link } from "react-router-dom";
import ExpenseForm from "./ExpenseForm";
import "../styles/Modal.css";
import "../styles/GroupCard.css";

const GroupCard = ({ groupId, groupName, addUserToGroup }) => {
  const [groupMembers, setGroupMembers] = useState([]);
  const [selectedGroupId, setSelectedGroupId] = useState(null);
  const [isGroupMembersVisible, setIsGroupMembersVisible] = useState(false);
  const [isExpenseFormVisible, setIsExpenseFormVisible] = useState(false);
  const [isExpensesVisible, setIsExpensesVisible] = useState(false);
  const [isExpensesModalVisible, setIsExpensesModalVisible] = useState(false);
  const [groupExpenses, setGroupExpenses] = useState([]);
  const [groupDebts, setGroupDebts] = useState([]);
  const [isDebtsModalVisible, setIsDebtsModalVisible] = useState(false);
  const [isSimpliDebtModalisVisible, setIsSimpliDebtModalisVisible] =
    useState(false);
  const [isSimplifyClicked, setIsSimplifyClicked] = useState(false);
  const [simplifiedDebts, setSimplifiedDebts] = useState([]); 
  const [currentPageDebts, setCurrentPageDebts] = useState(1);
  const [visibleDebts, setVisibleDebts] = useState([]);
  const [debtsPerPage] = useState(3); 
  const [showSuccessMessage, setShowSuccessMessage] = useState(false);

  const handleRemoveGroup = async (e) => {
    e.stopPropagation();

    try {
      const response = await fetch(`http://localhost:8080/groups/${groupId}`, {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
        },
      });

      if (response.ok) {
        console.log("Group deleted successfully");
        window.location.reload();
      } else {
        console.error("Error deleting group:", response.statusText);
      }
    } catch (error) {
      console.error("An error occurred while deleting group:", error);
    }
  };

  

  const handleSimplifyDebts = useCallback(async () => {
    try {
      const response = await fetch(
        `http://localhost:8080/simplify/${groupId}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      if (response.ok) {
        console.log("Simplify Debts response:", response);
        const simplifiedDebtsResult = await response.json();
        console.log("Simplified Debts Result:", simplifiedDebtsResult);
        setSimplifiedDebts(simplifiedDebtsResult);
        setIsSimpliDebtModalisVisible((prevValue) => !prevValue);
        setIsSimplifyClicked(false); // Reset the state after handling the click
        console.log("Simplified Debts:", simplifiedDebts);
      } else {
        console.error("Error simplifying debts:", response.statusText);
      }
    } catch (error) {
      console.error("An error occurred while simplifying debts:", error);
    }
  }, [groupId]);

  const renderSimplifiedDebts = useCallback(() => {
    if (simplifiedDebts.length === 0) {
      return <p>No simplified debts available.</p>;
    }

    return (
      <div>
        <h2>Simplified Debts</h2>
        {simplifiedDebts.map((debt) => (
          <div key={debt.id} className="simplified-debt-row">
            <p>
              <strong>Lender:</strong> {debt.lender.name}
            </p>
            <p>
              <strong>Borrower:</strong> {debt.borrower.name}
            </p>
            <p>
              <strong>Amount:</strong> {debt.amount}
            </p>
            <hr />
          </div>
        ))}
      </div>
    );
  }, [simplifiedDebts]);

  const handleSimplifyClick = async () => {
    setIsSimplifyClicked(true);
    handleSimplifyDebts();
  };

  useEffect(() => {
    if (isSimplifyClicked) {
      handleSimplifyDebts();
      setIsSimplifyClicked(false);
    }
  }, [isSimplifyClicked, groupId]);

  const itemsPerPage = 3;
  const [currentPage, setCurrentPage] = useState(1);

  const totalExpenses = groupExpenses.length;
  const totalPages = Math.ceil(totalExpenses / itemsPerPage);

  const visibleExpenses = groupExpenses.slice(
    (currentPage - 1) * itemsPerPage,
    currentPage * itemsPerPage
  );

  const handleAddEqualExpenseCSV = async () => {
    try {
      // Create an input element to trigger file selection
      const input = document.createElement("input");
      input.type = "file";
      input.accept = ".csv";
      input.click(); 

      // Handle file selection
      input.addEventListener("change", async (event) => {
        const file = event.target.files[0];

        if (file) {
          // Create FormData and append the file
          const formData = new FormData();
          formData.append("file", file);

          // Send the file to the server using fetch
          const response = await fetch("http://localhost:8080/expenses/csv", {
            method: "POST",
            body: formData,
          });

          if (response.ok) {
            console.log("File uploaded successfully");
            // Optionally, handle the response from the server
            setShowSuccessMessage(true);
            // Hide the success message after 3 seconds
            setTimeout(() => {
              setShowSuccessMessage(false);
            }, 2000);
          } else {
            console.error("Failed to upload file:", response.statusText);
            // Optionally, handle the error
          }
        }
      });
    } catch (error) {
      console.error("An error occurred while handling the file:", error);
    }
  };

  const handleViewDebts = async () => {
    try {
      const response = await fetch(
        `http://localhost:8080/debts/groupId/${groupId}?page=${currentPageDebts}&pageSize=${debtsPerPage}`
      );

      if (response.ok) {
        const debtsData = await response.json();
        // Toggle visibility when fetching debts
        setIsDebtsModalVisible((prevValue) => !prevValue);
        // Hide other sections when viewing debts
        setIsGroupMembersVisible(false);
        setIsExpenseFormVisible(false);
        setIsExpensesModalVisible(false);
        // Set the debts in the state
        setGroupDebts(debtsData);
      } else {
        console.error("Error fetching group debts:", response.statusText);
      }
    } catch (error) {
      console.error("An error occurred while fetching group debts:", error);
    }
  };
  useEffect(() => {
    // Update visible debts when groupDebts or currentPageDebts changes
    const startIndex = (currentPageDebts - 1) * debtsPerPage;
    const endIndex = startIndex + debtsPerPage;
    setVisibleDebts(groupDebts.slice(startIndex, endIndex));
  }, [groupDebts, currentPageDebts, debtsPerPage]);

  const handleNextPageDebts = () => {
    const totalPagesDebts = Math.ceil(groupDebts.length / debtsPerPage);
    if (currentPageDebts < totalPagesDebts) {
      setCurrentPageDebts(currentPageDebts + 1);
    }
  };

  const handlePrevPageDebts = () => {
    if (currentPageDebts > 1) {
      setCurrentPageDebts(currentPageDebts - 1);
    }
  };

  const handleNextPage = () => {
    if (currentPage < totalPages) {
      setCurrentPage(currentPage + 1);
    }
  };

  const handlePrevPage = () => {
    if (currentPage > 1) {
      setCurrentPage(currentPage - 1);
    }
  };

  const handleViewExpenses = async () => {
    try {
      const response = await fetch(
        `http://localhost:8080/expenses/groupId/${groupId}`
      );

      if (response.ok) {
        const expensesData = await response.json();
        // Handle expenses data as needed
        setIsExpensesVisible(!isExpensesVisible);
        // Hide group members when viewing expenses
        setIsGroupMembersVisible(false);
        setIsExpenseFormVisible(false);
        // Show expenses modal
        setIsExpensesModalVisible((prevValue) => !prevValue);
        setGroupExpenses(expensesData);
      } else {
        console.error("Error fetching group expenses:", response.statusText);
      }
    } catch (error) {
      console.error("An error occurred while fetching group expenses:", error);
    }
  };

  const handleViewMembers = async () => {
    try {
      const response = await fetch(
        `http://localhost:8080/usergroup/${groupId}/members`
      );

      if (response.ok) {
        const membersData = await response.json();
        setGroupMembers(membersData);
        setSelectedGroupId(groupId);
        // Toggle visibility when fetching members
        setIsGroupMembersVisible(!isGroupMembersVisible);
      } else {
        console.error("Error fetching group members:", response.statusText);
      }
    } catch (error) {
      console.error("An error occurred while fetching group members:", error);
    }
  };

  const renderGroupMembers = () => {
    if (groupMembers.length === 0) {
      return <p>No members in this group.</p>;
    }

    return (
      <div>
        {groupMembers.map((member) => (
          <div
            key={member.userId}
            style={{
              fontWeight: "bold",
              backgroundColor: "#F8D6B7",
              padding: "5px",
              margin: "5px 0",
            }}
          >
            {member.name}
          </div>
        ))}
      </div>
    );
  };

  const handleAddToGroup = (e) => {
    // Prevent the click event from propagating to the parent div
    e.stopPropagation();
    // Call the function to add the user to the group
    addUserToGroup(groupId);
  };

  const handleAddExpense = () => {
    // Open the expense form modal
    setIsExpenseFormVisible((prevValue) => !prevValue);
    setIsExpensesModalVisible(false);
  };

  const handleCloseExpenseForm = () => {
    // Close the expense form modal
    setIsExpenseFormVisible(false);
  };

  return (
    <div className="group-card-container">
      <div className="group-card-header">
        <h3 className="group-card-title">{groupName}</h3>
        <button className="remove-group-button" onClick={handleRemoveGroup}>
          Remove
        </button>
      </div>
      <div className="group-actions" style={{ marginTop: "30px" }}>
        <button className="group-action-button" onClick={handleAddToGroup}>
          Add User
        </button>
        <br />
        <br />
        <button className="group-action-button" onClick={handleViewMembers}>
          View Group Members
        </button>
        <br />
        {isGroupMembersVisible &&
          selectedGroupId === groupId &&
          renderGroupMembers()}
        <br />
        <button className="group-action-button" onClick={handleAddExpense}>
          Add Expense
        </button>
        <br />
        <br />
        <button className="group-action-button" onClick={handleViewExpenses}>
          {isExpensesModalVisible ? "Close Expenses" : "View Expenses"}
        </button>
        {isExpensesModalVisible && (
          <div className="modal-view-exp">
            <div className="modal-content-view-exp">
              <span
                className="close"
                onClick={() => setIsExpensesModalVisible(false)}
              >
                &times;
              </span>
              <h2>Group Expenses</h2>
              {visibleExpenses.map((expense) => (
                <div key={expense.id} className="expense-row">
                  <p>
                    <strong>Description:</strong> {expense.description}
                  </p>
                  <p>
                    <strong>Amount:</strong> {expense.amount}
                  </p>
                  <p>
                    <strong>Date:</strong>{" "}
                    {new Date(expense.date).toLocaleDateString()}
                  </p>
                  <p>
                    <strong>Payer:</strong> {expense.payer.name}
                  </p>
                  <p>
                    <strong>Group:</strong> {expense.group.name}
                  </p>
                  <hr />
                </div>
              ))}
              <div style={{ marginTop: "10px" }}>
                <button
                  className="group-action-button"
                  onClick={handlePrevPage}
                  disabled={currentPage === 1}
                >
                  Previous
                </button>{" "}
                <button
                  className="group-action-button"
                  onClick={handleNextPage}
                  disabled={currentPage === totalPages}
                >
                  Next
                </button>
              </div>
            </div>
          </div>
        )}
      </div>
      {isExpenseFormVisible && (
        <div className="modal-add">
          <div className="modal-content-add">
            <span className="close" onClick={handleCloseExpenseForm}>
              &times;
            </span>
            <ExpenseForm groupId={groupId} />
          </div>
        </div>
      )}
      <br />
      <button className="group-action-button" onClick={handleViewDebts}>
        View Debts
      </button>

      <div className={`modal ${isDebtsModalVisible ? "show" : ""}`}>
        <div className="modal-content">
          <span className="close" onClick={() => setIsDebtsModalVisible(false)}>
            &times;
          </span>
          <h2>Group Debts</h2>
          {visibleDebts.map((debt) => (
            <div key={debt.id} className="debt-row">
              <p>
                <strong>Lender:</strong> {debt.lender.name}
              </p>
              <p>
                <strong>Borrower:</strong> {debt.borrower.name}
              </p>
              <p>
                <strong>Amount:</strong> {debt.amount}
              </p>
              <hr />
            </div>
          ))}
          <div style={{ marginTop: "10px" }}>
            <button
              className="group-action-button"
              onClick={handlePrevPageDebts}
              disabled={currentPageDebts === 1}
            >
              Previous
            </button>{" "}
            <button
              className="group-action-button"
              onClick={handleNextPageDebts}
              disabled={currentPageDebts * debtsPerPage >= groupDebts.length}
            >
              Next
            </button>
          </div>
        </div>
      </div>

      <button
        className="group-action-button"
        onClick={() => handleSimplifyDebts()}
      >
        Simplify
      </button>

      <div
        className={`simpli-modal ${isSimpliDebtModalisVisible ? "show" : ""}`}
      >
        <div className="simpli-modal-content">
          <span
            className="close"
            onClick={() => setIsSimpliDebtModalisVisible(false)}
          >
            &times;
          </span>
          {isSimpliDebtModalisVisible && renderSimplifiedDebts()}
        </div>
      </div>
      <br />
      <button
        className="group-action-button"
        onClick={handleAddEqualExpenseCSV}
      >
        Add Equal Expense with CSV
      </button>
      {showSuccessMessage && (
        <div className="success-message">File uploaded successfully!</div>
      )}
    </div>
  );
};

export default GroupCard;
