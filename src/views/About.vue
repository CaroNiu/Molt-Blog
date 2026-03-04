<template>
  <div class="about">
    <div class="about-card">
      <div class="avatar">🐂</div>
      <h1>关于我</h1>
      <p class="bio">
        你好！我是 Caro，一名后端开发程序员。
      </p>
      <p class="bio">
        热爱技术，专注于 Java 后端开发，同时也对前端技术保持关注。
      </p>
      
      <div class="skills">
        <h2>技术栈</h2>
        <div class="skill-list">
          <span class="skill-tag">Java</span>
          <span class="skill-tag">Spring Boot</span>
          <span class="skill-tag">MySQL</span>
          <span class="skill-tag">Redis</span>
          <span class="skill-tag">Vue.js</span>
          <span class="skill-tag">Docker</span>
        </div>
      </div>

      <div class="contact">
        <h2>联系方式</h2>
        <p>GitHub: <a href="https://github.com/CaroNiu" target="_blank">@CaroNiu</a></p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.about {
  max-width: 800px;
  margin: 0 auto;
  padding: 2rem;
}

.about-card {
  background: #fff;
  border-radius: 16px;
  padding: 3rem;
  text-align: center;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
}

.avatar {
  font-size: 4rem;
  margin-bottom: 1rem;
}

.about-card h1 {
  font-size: 2rem;
  color: #1a1a1a;
  margin-bottom: 1rem;
}

.bio {
  color: #666;
  line-height: 1.8;
  margin-bottom: 1rem;
}

.skills,
.contact {
  margin-top: 2rem;
  text-align: left;
}

.skills h2,
.contact h2 {
  font-size: 1.25rem;
  color: #1a1a1a;
  margin-bottom: 1rem;
}

.skill-list {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
}

.skill-tag {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 0.5rem 1rem;
  border-radius: 20px;
  font-size: 0.875rem;
}

.contact p {
  color: #666;
}

.contact a {
  color: #3b82f6;
  text-decoration: none;
}

.contact a:hover {
  text-decoration: underline;
}
</style>