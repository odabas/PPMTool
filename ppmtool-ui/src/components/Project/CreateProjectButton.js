import React from "react";
import { Link } from "react-router-dom";

const CreateProjectButton = () => {
  return (
    <React.Fragment>
      <Link
        to="/addProject"
        href="ProjectForm.html"
        className="btn btn-lg btn-info"
      >
        Crete a Project
      </Link>
    </React.Fragment>
  );
};

export default CreateProjectButton;
