/** @type {import('next').NextConfig} */
const nextConfig = {
    images: {
        domains: ['res.cloudinary.com'],
      },
}

module.exports = {
  nextConfig,
  output: 'standalone',
  images: {
    domains: ['res.cloudinary.com'],
  },
}
