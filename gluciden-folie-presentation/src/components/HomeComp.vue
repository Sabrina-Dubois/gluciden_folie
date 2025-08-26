<template>
  <v-container class="home-comp-container" fluid>
    <h1>{{ $t("home.title") }}</h1>

    <v-card class="d-flex mb-4 flex-wrap" elevation="4">
      <h2 class="section-title">{{ $t("home.recipeOfTheDay.title") }}</h2>

      <v-container fluid>
        <v-row
          class="recipe-row"
          align="center"
          justify="space-between"
        >
          <!-- Image (affichée en premier si mobile) -->
          <v-col
            cols="12"
            md="6"
            class="image-col"
            :class="{ 'order-1': isMobile }"
          >
            <v-card flat>
              <v-img
                :src="cards[0].src"
                :alt="cards[0].title"
                class="recipe-image"
                :class="{ 'mobile-square': isMobile }"
                cover
                rounded
              >
                <v-card-title class="card-title">
                  {{ cards[0].title }}
                </v-card-title>
              </v-img>

              <v-card-actions class="d-flex">
                <v-spacer></v-spacer>
                <v-btn icon="mdi-heart" aria-label="$t('home.recipeOfTheDay.buttons.addToFavorites')"></v-btn>
                <v-btn icon="mdi-share-variant" aria-label="$t('home.recipeOfTheDay.buttons.shareRecipe')"></v-btn>
                <v-spacer></v-spacer>
              </v-card-actions>
            </v-card>
          </v-col>

          <!-- Description -->
          <v-col
            cols="12"
            md="6"
            class="description-col"
            :class="{ 'order-2': isMobile }"
          >
            <p class="description">
              {{ $t("home.recipeOfTheDay.description") }}
            </p>
          </v-col>
        </v-row>
      </v-container>
    </v-card>

    <!-- Carrousel -->
    <v-card>
      <h2>{{ $t("home.foodieFavorite.title") }}</h2>
      <v-card class="carroussel" elevation="4">
        <v-carousel hide-delimiters>
          <v-carousel-item
            v-for="(item, i) in items"
            :key="i"
            :src="item.src"
            cover
          >
            <div class="text">{{ item.title }}</div>
            <img :src="item.src" :alt="item.title" class="d-none" />
          </v-carousel-item>
        </v-carousel>
      </v-card>
    </v-card>
  </v-container>
</template>

<script>
export default {
  name: "homeComp",
  data() {
    return {
      items: [
        { src: "/images/Pancakes.jpg", title: "Pancakes" },
        { src: "/images/Tarte aux pommes.jpg", title: "Tarte aux pommes" },
        { src: "/images/Pudding chia coco.jpg", title: "Pudding chia coco" },
        { src: "/images/Banana bread.jpeg", title: "Banana bread" },
      ],
      cards: [
        {
          title: "Carrot cake",
          src: "https://flipdish.imgix.net/y1Xqez7kfY0ueFmsx4BA7agj0Y.jpg",
        },
      ],
      isMobile: false,
    };
  },
  mounted() {
    this.checkScreenSize();
    window.addEventListener("resize", this.checkScreenSize);
  },
  beforeUnmount() {
    window.removeEventListener("resize", this.checkScreenSize);
  },
  methods: {
    checkScreenSize() {
      this.isMobile = window.innerWidth <= 768;
    },
  },
};
</script>

<style scoped>
/* *** Container *** */
.home-comp-container {
  max-width: 900px;
  margin: auto;
  padding: 0 16px;
}
.mb-4 {
  margin-bottom: 16px;
}

/* *** Section titre *** */
.section-title {
  margin: 16px 0;
  font-size: 24px;
  color: #5d827f;
}

/* *** Image *** */
.recipe-image {
  width: 100%;
  height: 300px; /* Grande image en desktop */
}
.mobile-square {
  aspect-ratio: 1 / 1; /* Carrée en mobile */
  height: auto !important;
}

/* *** Description *** */
.description {
  color: #5d827f;
  font-size: 20px;
  text-align: center;
  padding: 16px;
}

/* *** Titre image *** */
.card-title {
  color: white;
  font-size: 25px;
  text-align: top;
}

/* *** Bouton *** */
.v-btn {
  color: #5d827f;
  margin: 0px 20px;
  background-color: #f5ede8;
}
.v-btn:hover,
.v-btn--active {
  background-color: #5d827f;
  color: #f5ede8 !important;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.3);
}

/* *** Carrousel *** */
.text {
  position: absolute;
  width: 100%;
  text-align: center;
  color: white;
  font-size: 25px;
  padding: 10px;
}
.carroussel {
  overflow: visible;
  max-width: 900px;
  margin: auto;
}

/* *** Gestion ordre mobile *** */
.order-1 {
  order: 1;
}
.order-2 {
  order: 2;
}
</style>
