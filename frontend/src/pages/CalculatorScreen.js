import React from "react";
import Calculator from "./Calculator"; 
import NavBar from "../pages/NavBar";
import Footer from "../pages/Footer";
const CalculatorScreen = () => {


  return (
    <div>
      <NavBar />
      <h1>Calculator Screen</h1>
      <Calculator />
      <Footer />
    </div>
  );
};

export default CalculatorScreen;
