import React, { useState, useEffect } from "react";
import Modal from "react-modal";
import '../styles/expense.css';

const ExpenseForm = ({ groupId }) => {
  const [expenseType, setExpenseType] = useState("equal");
  const [description, setDescription] = useState("");
  const [amount, setAmount] = useState("");
  const [payersList, setPayersList] = useState([]);
  const [payer, setPayer] = useState("");
  const [group, setGroup] = useState([]);
  const [date, setDate] = useState("");
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [borrowers, setBorrowers] = useState([]);
  const [availableUsers, setAvailableUsers] = useState([]);

  const handleBorrowerChange = (index, e) => {
    const updatedBorrowers = [...borrowers];
    updatedBorrowers[index].userId = e.target.value;
    const selectedUser = availableUsers.find((user) => user.userId === Number(e.target.value));
    console.log("something is happening", availableUsers.find((user) => user.userId === e.target.value))
    console.log("e value is:", e.target.value);
    console.log("available borrower console is ", availableUsers);
    console.log("borrower user console is:", selectedUser);
    if (selectedUser) {
      updatedBorrowers[index].name = selectedUser.name || "";
      updatedBorrowers[index].email = selectedUser.email || "";
    }
    setBorrowers(updatedBorrowers);
    console.log("borrowers console is:", borrowers);
  };
  

  const handleBorrowerAmountChange = (index, e) => {
    const updatedBorrowers = [...borrowers];
    updatedBorrowers[index].amount = e.target.value;
    setBorrowers(updatedBorrowers);
  };


  const handleRemoveBorrower = (index) => {
    const updatedBorrowers = [...borrowers];
    updatedBorrowers.splice(index, 1);
    setBorrowers(updatedBorrowers);
  };

  const handleAddBorrower = () => {
    setBorrowers([...borrowers, { userId: "", name: "", email: "", amount: "" }]);
  };
  

  const handleBorrowerEmailChange = (index, e) => {
    const updatedBorrowers = [...borrowers];
    updatedBorrowers[index].email = e.target.value;
    setBorrowers(updatedBorrowers);
  };
  

  useEffect(() => {
    console.log("Payer is:", payer);
    console.log("Payers list is:", payersList);
    console.log("Available users are:", availableUsers);
    fetchUsersInGroup(groupId)
      .then((users) => {
        setPayersList(users);
        setAvailableUsers(users);
      })
      .catch((error) => {
        console.error("Error fetching users:", error);
      });
  }, [groupId]);

  useEffect(() => {
    fetchGroupInfo(groupId)
      .then((groupData) => {
        setGroup(groupData);
      })
      .catch((error) => {
        console.error("Error fetching group:", error);
      });
  }, [groupId]);

  const customModalStyles = {
    content: {
      width: "50%",
      height: "50%",
      top: "50%",
      left: "50%",
      right: "auto",
      bottom: "auto",
      marginRight: "-50%",
      transform: "translate(-50%, -50%)",
    },
  };

  const handleExpenseTypeChange = (e) => {
    setExpenseType(e.target.value);
  };

  const handlePayerChange = (e) => {
    setPayer(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Convert date to the desired format
    const formattedDate = new Date(date).toISOString();

    const selectedPayer = payersList.find((user) => user.name === payer);
    console.log("payersList console is:", payersList);
    console.log("selectedPayer console is:", selectedPayer);
    console.log("payer console is:", payer);

    const requestBody = {
      description,
      amount,
      payer: {
        id: selectedPayer?.userId || "",
        name: selectedPayer?.name || "",
        email: selectedPayer?.email || "",
      },
      group: {
        id: group.id,
        name: group.name,
      },
      date: formattedDate,
    };

    console.log("requestBody", requestBody);
    // Include borrower information if the expense type is "unequal"
    if (expenseType === "unequal") {
      const borrowerData = borrowers.map((borrower) => ({
        user: {
          name: borrower.name,
          email: borrower.email,
        },
        amount: borrower.amount,
      }));

      requestBody.borrower = borrowerData;
    }

    try {
      const response = await fetch(
        expenseType === "equal"
          ? "http://localhost:8080/expenses"
          : "http://localhost:8080/expenses/unequal",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(requestBody),
        }
      );

      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }

      console.log("Expense data sent successfully:", requestBody);

      // Open the modal on successful submission
      setIsModalOpen(true);

      setExpenseType("equal");
      setDescription("");
      setAmount("");
      setPayer("");
      setDate("");
    } catch (error) {
      console.error("Error sending expense data:", error);
    }
  };

  const closeModal = () => {
    setIsModalOpen(false);
    setExpenseType("equal");
    setDescription("");
    setAmount("");
    setPayer("");
    setDate("");
  };

  return (
    <div>
      <form onSubmit={handleSubmit}>
        <label>
          Expense Type:
          <select value={expenseType} onChange={handleExpenseTypeChange}>
            <option value="equal">Split Equally</option>
            <option value="unequal">Split Unequally</option>
          </select>
        </label>
        <br />

        <label>
          Description:
          <input
            type="text"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
          />
        </label>
        <br />

        <label>
          Amount:
          <input
            type="number"
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
          />
        </label>
        <br />

        <label>
          Payer:
          <select value={payer} onChange={handlePayerChange}>
            <option value="" disabled>
              Select Payer
            </option>
            {payersList.map((user) => (
              <option key={user.id} value={user.id}>
                {user.name}
              </option>
            ))}
            onChange={(e) => setPayer(e.target.value)}
          </select>
        </label>
        <br />

        <label>
          Date:
          <input
            type="date"
            value={date}
            onChange={(e) => setDate(e.target.value)}
          />
        </label>
        <br />

        {expenseType === "unequal" && (
          <div>
            <label>Borrowers:</label>
            {borrowers.map((borrower, index) => (
              <div key={index}>
                <select
                  value={borrower.userId || ""}
                  onChange={(e) => handleBorrowerChange(index, e)}
                >
                  {/* ... options for users */}
                  {availableUsers.map((user) => (
                    <option key={user.id} value={user.userId}>
                      {user.name}
                    </option>
                  ))}
                </select>
                <input
                  type="number"
                  placeholder="Amount"
                  value={borrower.amount || ""}
                  onChange={(e) => handleBorrowerAmountChange(index, e)}
                />
                <button
                  type="button"
                  onClick={() => handleRemoveBorrower(index)}
                >
                  Remove
                </button>
              </div>
            ))}

            <button type="button" onClick={handleAddBorrower}>
              Add Borrower
            </button>
          </div>
        )}

        <button type="submit">Submit</button>
      </form>

      <Modal
        isOpen={isModalOpen}
        onRequestClose={closeModal}
        contentLabel="Expense Submitted Successfully"
        style={customModalStyles}
      >
        <h2>Expense Submitted Successfully</h2>
        <button onClick={closeModal}>Close Modal</button>
      </Modal>
    </div>
  );
};

const fetchUsersInGroup = async (groupId) => {
  const response = await fetch(
    `http://localhost:8080/usergroup/${groupId}/members`
  );

  return response.json();
};

const fetchGroupInfo = async (groupId) => {
  const response = await fetch(`http://localhost:8080/groups/${groupId}`);

  return response.json();
};

export default ExpenseForm;
