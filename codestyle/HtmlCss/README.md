# 📖 Essential HTML & CSS Style Guide

## 📋 Table of Contents

### 🌐 HTML
1. [HTML Syntax](#1-html-syntax)
2. [Accessibility](#2-accessibility)
3. [Buttons](#3-buttons)
4. [Images](#4-images)
5. [Forms](#5-forms)

### 🎨 CSS
6. [CSS Methodologies](#6-css-methodologies)
7. [Naming Conventions](#7-naming-conventions)
8. [Z-Index](#8-z-index)
9. [Animations](#9-animations)
10. [Colors and Variables](#10-colors-and-variables)
11. [Sass](#11-sass)
12. [File Structure](#12-file-structure)

---

# 🌐 HTML

## 1. HTML Syntax

**✅ CORRECT:**
```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Descriptive Title - Website</title>
</head>
<body>
    <header>
        <nav>
            <ul>
                <li><a href="/">Home</a></li>
                <li><a href="/products">Products</a></li>
            </ul>
        </nav>
    </header>
    
    <main>
        <article>
            <h1>Article Title</h1>
            <p>Article content...</p>
        </article>
    </main>
    
    <footer>
        <p>&copy; 2024 My Company</p>
    </footer>
</body>
</html>
```

**❌ INCORRECT:**
```html
<html>
<head>
    <title>Page</title>
</head>
<body>
    <div class="header">
        <div class="nav">
            <div class="item">Home</div>
        </div>
    </div>
</body>
</html>
```

**Why it matters:** Semantic HTML improves accessibility, SEO, and code maintainability. Elements like `<header>`, `<nav>`, `<main>` communicate structure to browsers and screen readers.

---

## 2. Accessibility

**✅ CORRECT:**
```html
<a href="#main-content" class="skip-link">Skip to content</a>

<nav aria-label="Main navigation">
    <ul>
        <li><a href="/" aria-current="page">Home</a></li>
    </ul>
</nav>

<main id="main-content">
    <h1>Main Title</h1>
    <h2>Subtitle</h2>
</main>

<button aria-expanded="false" aria-controls="menu">
    Menu
</button>
```

**❌ INCORRECT:**
```html
<div onclick="doSomething()">Clickable</div>
<h1>Title</h1>
<h3>Subtitle</h3>
<div class="button">Button</div>
```

**Why it matters:** Accessibility ensures all people can use your website. ARIA attributes and proper heading hierarchy are essential for screen readers.

---

## 3. Buttons

**✅ CORRECT:**
```html
<button type="button" class="btn btn--primary">
    Primary Action
</button>

<button type="submit" disabled>
    Submitting...
</button>

<a href="/contact" class="btn" role="button">
    Go to Contact
</a>
```

**❌ INCORRECT:**
```html
<div onclick="submit()">Submit</div>
<a href="#" onclick="doSomething()">Click here</a>
<input type="button" value="Submit form">
```

**Why it matters:** `<button>` elements are natively accessible via keyboard and screen readers. Clickable divs require additional JavaScript for basic accessibility functionality.

---

## 4. Images

**✅ CORRECT:**
```html
<img 
    src="image.jpg" 
    alt="Specific description"
    width="300" 
    height="200"
    loading="lazy"
>

<picture>
    <source srcset="image.webp" type="image/webp">
    <img src="image.jpg" alt="Description">
</picture>

<img 
    src="placeholder.jpg"
    data-src="real-image.jpg"
    alt="Description"
    onerror="this.src='fallback.jpg'"
>
```

**❌ INCORRECT:**
```html
<img src="image.jpg">
<img src="image.jpg" alt="image">
<div style="background-image: url(content.jpg)"></div>
```

**Why it matters:** The `alt` attribute is crucial for accessibility. `loading="lazy"` improves performance. Explicit dimensions prevent layout shift.

---

## 5. Forms

**✅ CORRECT:**
```html
<form>
    <div class="form-group">
        <label for="name">Name *</label>
        <input 
            type="text" 
            id="name" 
            name="name" 
            required 
            aria-describedby="name-error"
        >
        <div id="name-error" role="alert"></div>
    </div>
    
    <div class="form-group">
        <input type="checkbox" id="newsletter">
        <label for="newsletter">Subscribe</label>
    </div>
    
    <button type="submit">Submit</button>
</form>
```

**❌ INCORRECT:**
```html
<form>
    <input type="text" placeholder="Name">
    <input type="checkbox"> Newsletter
    <div onclick="submit()">Submit</div>
</form>
```

**Why it matters:** Properly associated labels improve accessibility and UX. Placeholders don't replace labels. Error messages with `role="alert"` are automatically announced.

---

# 🎨 CSS

## 6. CSS Methodologies

### BEM (Block-Element-Modifier)

**✅ CORRECT:**
```css
.card {
  background: white;
  padding: 20px;
}

.card__title {
  font-size: 1.5rem;
}

.card__button {
  background: #007bff;
  padding: 8px 16px;
}

.card--featured {
  border: 2px solid gold;
}
```

**❌ INCORRECT:**
```css
.card .title { }
.card-red { }
.sidebar .card .header .title { }
```

**Why BEM is better:** Avoids specificity conflicts, facilitates maintenance, and makes code more predictable. Each class is independent and reusable.

### OOCSS

**✅ CORRECT:**
```css
.layout-flex {
  display: flex;
}

.theme-primary {
  background: #007bff;
  color: white;
}
```

**Why it works:** Separates visual skin from layout structure, allowing flexible combinations without code duplication.

---

## 7. Naming Conventions

**✅ CORRECT:**
```css
.product-card { }
.navigation-menu { }
.is-active { }
.text-center { }
.js-modal-trigger { }
```

**❌ INCORRECT:**
```css
.red-box { }
.big-text { }
.userProfile { }
```

**Why it matters:** Descriptive names based on function (not appearance) make code self-documenting and resistant to design changes.

---

## 8. Z-Index

**✅ CORRECT:**
```scss
$z-layers: (
  'header': 200,
  'dropdown': 300,
  'modal': 500,
  'tooltip': 600
);

.header {
  z-index: map-get($z-layers, 'header');
}
```

**❌ INCORRECT:**
```css
.header { z-index: 999; }
.modal { z-index: 99999; }
```

**Why organize it:** Avoids the "z-index war" where values grow out of control. An organized system is predictable and maintainable.

---

## 9. Animations

**✅ CORRECT:**
```css
@media (prefers-reduced-motion: no-preference) {
  .fade-in {
    animation: fadeIn 0.3s ease-out;
  }
}

.button {
  transition: transform 0.2s ease;
}

.button:hover {
  transform: translateY(-2px);
}
```

**❌ INCORRECT:**
```css
.bad-animation {
  transition: width 0.3s, height 0.3s;
}

.always-animate {
  animation: spin 2s infinite;
}
```

**Why these practices:** `prefers-reduced-motion` respects accessibility preferences. Animating `transform` and `opacity` is more efficient than properties that cause reflow.

---

## 10. Colors and Variables

**✅ CORRECT:**
```css
:root {
  --color-primary-500: #007bff;
  --color-primary-600: #0056b3;
  --color-gray-100: #f3f4f6;
  --color-success: #10b981;
  --color-error: #ef4444;
  
  --text-primary: var(--color-gray-900);
  --text-secondary: var(--color-gray-600);
}

.button {
  background: var(--color-primary-500);
  color: var(--text-primary);
}
```

**❌ INCORRECT:**
```css
.button { background: #3498db; }
.header { background: #2980b9; }

:root {
  --blue: #3498db;
}
```

**Why use variables:** Centralizes color management, facilitates theming and maintenance. Semantic names are clearer than specific colors.

---

## 11. Sass

**✅ CORRECT:**
```scss
@mixin button-base($padding: 8px 16px) {
  display: inline-flex;
  padding: $padding;
  border: none;
  cursor: pointer;
}

@function rem($pixels, $context: 16) {
  @return ($pixels / $context) * 1rem;
}

%visually-hidden {
  position: absolute;
  width: 1px;
  height: 1px;
  overflow: hidden;
}

.button {
  @include button-base();
  font-size: rem(16px);
}
```

**❌ INCORRECT:**
```scss
@mixin blue-button {
  background: blue;
  border-radius: 5px;
}

.page {
  .section {
    .container {
      .widget { }
    }
  }
}
```

**Why these techniques:** Reusable mixins reduce duplication. Functions automate calculations. Limiting nesting keeps specificity low.

---

## 12. File Structure

**✅ CORRECT:**
```
scss/
├── abstracts/
│   ├── _variables.scss
│   └── _mixins.scss
├── base/
│   ├── _reset.scss
│   └── _typography.scss
├── components/
│   ├── _buttons.scss
│   └── _cards.scss
├── layout/
│   ├── _header.scss
│   └── _grid.scss
└── main.scss
```

**main.scss:**
```scss
@import 'abstracts/variables';
@import 'base/reset';
@import 'components/buttons';
@import 'layout/header';
```

**❌ INCORRECT:**
```
css/
├── style.css
├── homepage.css
└── fixes.css
```

**Why this organization:** Modular architecture facilitates maintenance, team collaboration, and scalability. Each file has a clear responsibility.

---

## 📚 Essential Tools

- **Linting**: Stylelint, HTMLHint, Prettier
- **Accessibility**: axe DevTools, WAVE  
- **Performance**: Lighthouse
- **Build**: Sass, PostCSS, PurgeCSS
