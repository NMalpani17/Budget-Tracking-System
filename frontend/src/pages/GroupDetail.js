// GroupDetail.js
import React from "react";
import { Link, useLocation  } from "react-router-dom";
import "../styles/groupDetail.css";

const GroupDetail = () => {
  const location = useLocation();
  const { state } = location;
    console.log("GroupDetail props:", location);
    const { groupId, groupName, groupMembers } = state;
  return (
    <div>
      <h2>{groupName}</h2>
      <h3>Group Members:</h3>
      <ul>
        {groupMembers.map((member) => (
          <li key={member.userId}>Member Name: {member.name}</li>
        ))}
      </ul>
      <Link to={`/group/${groupId}/add-expense`}>
        <button>Add Expense</button>
      </Link>
    </div>
  );
};

export default GroupDetail;
