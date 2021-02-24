<template>
  <nav class="col-md-2 d-none d-md-block bg-light sidebar">
    <div class="sidebar-sticky">
      <ul class="nav flex-column">
        <li class="nav-item">
          <router-link to="/"><a class="nav-link active" href="#">
            Моя страница
          </a></router-link>
        </li>
        <li class="nav-item">
          <router-link to="/users"><a class="nav-link" href="#">
            Пользователи
          </a></router-link>
        </li>
        <li class="nav-item">
          <router-link to="/friends/all"><a class="nav-link" href="#">
            Друзья
          </a></router-link>
        </li>
        <li class="nav-item">
          <router-link to="/wall"><a class="nav-link" href="#">
            Лента новостей
          </a></router-link>
        </li>
      </ul>

    </div>
  </nav>
</template>

<script>
import Router from "@/router";

export default {
  name: 'Sidebar',
  methods: {}
}
</script>
