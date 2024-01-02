import { useState } from "react";

function Calculator() {
  const [num1, setNum1] = useState("");
  const [num2, setNum2] = useState("");
  const [operation, setOperation] = useState("+");
  const [result, setResult] = useState("");
  const [operations, setOperations] = useState([]);
  const [showOperations, setShowOperations] = useState(false);

  const toggleOperations = () => {
    setShowOperations(!showOperations);
  };

  const isNumeric = (value) => {
    return !isNaN(parseFloat(value)) && isFinite(value);
  };

  const handleNum1Change = (e) => {
    if (isNumeric(e.target.value) || e.target.value === "") {
      setNum1(e.target.value);
    }
  };

  const handleNum2Change = (e) => {
    if (isNumeric(e.target.value) || e.target.value === "") {
      setNum2(e.target.value);
    }
  };

  const calculate = async () => {
    try {
      const response = await fetch(
        `http://localhost:8081/${operation}/${num1}/${num2}`
      );
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      const data = await response.text();
      setResult(data);
    } catch (error) {
      setResult(`Error: ${error.message}`);
    }
  };

  const showCalulations = async () => {
    try {
      const response = await fetch(`http://localhost:8081/getOperations`);
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      const data = await response.json();
      setOperations(data);
      showOperations ? setShowOperations(false) : setShowOperations(true);
    } catch (error) {
      setOperations(`Error: ${error.message}`);
    }
  };

  const clearInputs = () => {
    setNum1("");
    setNum2("");
    setOperation("+");
    setResult("");
  };

  return (
    <div style={calculatorStyles}>
      <input
        type="text"
        value={num1}
        onChange={handleNum1Change}
        placeholder="Enter number"
        style={inputStyles}
      />
      <select
        onChange={(e) => setOperation(e.target.value)}
        style={selectStyles}
      >
        <option value="+">+</option>
        <option value="-">-</option>
        <option value="*">*</option>
        <option value="/">/</option>
      </select>
      <input
        type="text"
        value={num2}
        onChange={handleNum2Change}
        placeholder="Enter number"
        style={inputStyles}
      />
      <button onClick={calculate} style={buttonStyles}>
        Calculate
      </button>
      <button onClick={clearInputs} style={clearButtonStyles}>
        Clear
      </button>
      <div style={resultStyles}>Result: {result}</div>
      <button onClick={showCalulations} style={buttonStyles}>
        Show Operations
      </button>
      {showOperations && (
        <div>
          <strong>Operations:</strong>
          {operations.map((operation, index) => (
            <div key={index}>
               {operation.Operand1} {operation.operation} {operation.Operand2}
            </div>
          ))}
        </div>
       )} 
    </div>
    // </div>
  );
}

const calculatorStyles = {
  display: "flex",
  flexDirection: "column",
  alignItems: "center",
  padding: "20px",
  border: "1px solid #ccc",
  borderRadius: "8px",
  maxWidth: "300px",
  margin: "auto",
  marginTop: "50px",
};

const inputStyles = {
  margin: "5px",
  padding: "8px",
  fontSize: "16px",
};

const selectStyles = {
  margin: "5px",
  padding: "8px",
  fontSize: "16px",
};

const buttonStyles = {
  margin: "10px",
  padding: "10px",
  fontSize: "16px",
  backgroundColor: "#4CAF50",
  color: "white",
  border: "none",
  borderRadius: "5px",
  cursor: "pointer",
};

const clearButtonStyles = {
  margin: "10px",
  padding: "10px",
  fontSize: "16px",
  backgroundColor: "#f44336",
  color: "white",
  border: "none",
  borderRadius: "5px",
  cursor: "pointer",
};

const resultStyles = {
  marginTop: "10px",
  fontSize: "18px",
};

export default Calculator;
