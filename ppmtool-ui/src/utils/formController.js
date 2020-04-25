import classnames from "classnames";
import React from "react";

class formController {
  static checkError = (hasError) => {
    return classnames("form-control form-control-lg", {
      "is-invalid": hasError,
    });
  };

  static addErrorDescription(errorDescription) {
    return (
      errorDescription && (
        <div className="invalid-feedback">{errorDescription}</div>
      )
    );
  }
}

export default formController;
