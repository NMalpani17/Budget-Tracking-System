import logo from "./logo.svg";
import "./App.css";
import SignupForm from "./pages/SignupForm";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import LoginForm from "./pages/LoginForm";
import CreateGroup from "./pages/CreateGroup";
import GroupDetail from "./pages/GroupDetail";
import CalculatorScreen from "./pages/CalculatorScreen";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<SignupForm />} />
        <Route path="/login" element={<LoginForm />} />
        <Route path ="/creat-group" element = {<CreateGroup/>} />
        <Route path="/group/:groupId" element={<GroupDetail/>} />
        <Route path="/calculator" element={<CalculatorScreen />} />
      </Routes>
    </Router>
  );
}

export default App;
