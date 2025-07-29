import axios from "axios";

axios
  .get("http://localhost:8080/api/endpoint")
  .then((response) => {
    console.log(response.data);
  })
  .catch((error) => {
    console.error("Erreur réseau:", error);
  });
