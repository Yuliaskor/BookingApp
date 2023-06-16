import './globals.css'
import { Nunito } from 'next/font/google'
import Navbar from '@/app/components/navbar/Navbar';
import ClientOnly from './ClientOnly';
import RegisterModal from './modal/RegisterModal';
import ToasterProvider from './providers/ToasterProvider';
import LoginModal from './modal/LoginModal';
import getCurrentUser from './action/getCurrentUser';
import RentModel from './modal/RentModal';
import SearchModal from './modal/SearchModal';
import ReserveModal from './modal/ReserveModal';

const font = Nunito({ 
  subsets: ['latin'], 
});

export const metadata = {
  title: 'Airbnb',
  description: 'Airbnb Clone',
}

export default async function RootLayout({
  children,
}: {
  children: React.ReactNode
}) {
  const currentUser = await getCurrentUser();
  console.log("");
  console.log(currentUser);
  return (
    <html lang="en">
      <body className={font.className}>
        <ClientOnly>
          <ToasterProvider></ToasterProvider>
          <LoginModal></LoginModal>
          <SearchModal></SearchModal>
          <ReserveModal></ReserveModal>
          <RegisterModal></RegisterModal>
          <RentModel></RentModel>
           <Navbar currentUser={currentUser} />
        </ClientOnly>
        <div className='pb-20 pt-28'>
         {children}
        </div>
       </body>
    </html>
  )
}
