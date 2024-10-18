<template>
    <div class="main-content custom-bg">
        <h1>{{ $t("update_recipe.page.title") }}</h1>
        <v-card class="recipeForm d-flex align-center">
            <v-form @submit.prevent="updateRecipe">
                <h3>{{ $t("update_recipe.page.recipe_title.recipe_title") }}</h3>
                <v-text-field
                    v-model="recipeName"
                    label="Nom de la recette"
                    hide-details
                    variant="underlined"
                ></v-text-field>

                <h3>{{ $t("update_recipe.page.picture.picture") }}</h3>

                <!-- Afficher l'image existante ou nouvellement téléchargée -->
                <v-img
                    v-if="imagePreview"
                    :src="imagePreview"
                    class="recipe-picture"
                    height="200px"
                    cover
                    rounded=""
                ></v-img>

                <!-- Champ pour télécharger une nouvelle image -->
                <v-file-input
                    @change="handleFileUpload"
                    accept="image/*"
                    label="Télécharger la photo de la recette"
                    chips
                    variant="underlined"
                ></v-file-input>

                <v-btn class="custom-btn" ml-5 rounded="" @click="updateRecipe" type="submit">{{
                    $t("update_recipe.page.button")
                }}</v-btn>
            </v-form>
        </v-card>
    </div>
</template>

<script>
export default {
    name: "updateRecipe",
    data() {
        return {
            id: this.$route.params.id,  // Récupération de l'ID de la recette
            recipeName: "",
            recipePicture: null,
            imagePreview: null
        };
    },
    beforeMount() {
        this.initRecipe();
    },
    methods: {
        async initRecipe() {
            try {
                const response = await fetch(`http://localhost:8080/recipes/${this.id}`);
                const json = await response.json();
                this.recipeName = json.name;
                this.imagePreview = `/images/${json.picture}`;  // Afficher l'image existante
            } catch (error) {
                console.error("Erreur lors de la récupération de la recette:", error);
            }
        },
        // Gestion du téléchargement de l'image
        handleFileUpload(event) {
            const file = event.target.files[0];
            if (file) {
                this.recipePicture = file;
                this.imagePreview = URL.createObjectURL(file);  // Afficher l'image téléchargée
            }
        },
        // Mise à jour de la recette
        async updateRecipe() {
            const formData = new FormData();
            formData.append("name", this.recipeName);

            if (this.recipePicture) {
                formData.append("picture", this.recipePicture);  // Ajouter la nouvelle image si elle est modifiée
            }

            try {
                const response = await fetch(`http://localhost:8080/recipes/${this.id}`, {
                    method: "PUT",
                    body: formData,
                });

                if (response.ok) {
                    console.log("Recette mise à jour avec succès !");
					this.$router.push({ name: 'recipesList' });
                } else {
                    console.error("Erreur lors de la mise à jour de la recette");
                }
            } catch (error) {
                console.error("Erreur réseau lors de la mise à jour:", error);
            }
        }
    }
};
</script>

<style scoped>
.main-content.custom-bg {
    padding-top: 10px;
}

.d-flex {
    align-items: center;
    justify-content: center;
}

h1 {
    text-align: center;
    margin: 20px;
}

h3 {
    color: #5d827f;
}

.recipeForm {
    max-width: 900px;
    margin: auto;
}

.v-card {
    background-color: white;
    text-align: center;
    margin: 0 auto;
    padding: 20px;
}

.v-text-field,
.v-file-input {
    margin-bottom: 20px;
    color: #5d827f;
}

.v-btn {
    background-color: #5d827f;
    color: #d3beb1;
}

.recipe-picture {
    margin-top: 20px;
}
</style>