/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  important: '#root', // Ensures Tailwind utilities override global CSS and don't conflict with MUI
  theme: {
    extend: {},
  },
  plugins: [],
}
