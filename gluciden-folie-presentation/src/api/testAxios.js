import axios from "axios";

axios
  .get("http://localhost:8080/api/endpoint")
  .catch((error) => {
    console.error("Erreur réseau:", error);
  });
