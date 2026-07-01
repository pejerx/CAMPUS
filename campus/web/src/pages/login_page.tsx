import { useState } from "react";
import { Modal, Box, Button } from "@mui/material";

import "../css/component_style.css";

function LoginModal() {
  const [open, setOpen] = useState(false);

  return (
    <>
      <Button
        variant="contained"
        onClick={() => setOpen(true)}
      >
        Student Login
      </Button>

      <Modal
        open={open}
        onClose={() => setOpen(false)}
      >
        <Box className="modal-container">

          <h2>Student Login</h2>

          <input
            type="email"
            placeholder="Email"
          />

          <input
            type="password"
            placeholder="Password"
          />

          <Button
            variant="contained"
            fullWidth
          >
            Login
          </Button>

        </Box>
      </Modal>
    </>
  );
}

export default LoginModal;