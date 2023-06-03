import './globals.css'
import { Nunito } from 'next/font/google'
import Navbar from '@/app/components/navbar/Navbar';
import ClientOnly from './ClientOnly';
import RegisterModal from './modal/RegisterModal';
import ToasterProvider from './providers/ToasterProvider';
import LoginModal from './modal/LoginModal';

const font = Nunito({ 
  subsets: ['latin'], 
});

export const metadata = {
  title: 'Airbnb',
  description: 'Airbnb Clone',
}

export default function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return (
    <html lang="en">
      <body className={font.className}>
        <ClientOnly>
          <ToasterProvider></ToasterProvider>
          <LoginModal></LoginModal>
          <RegisterModal></RegisterModal>
          
           <Navbar />
        </ClientOnly>
        {children}</body>
    </html>
  )
}
