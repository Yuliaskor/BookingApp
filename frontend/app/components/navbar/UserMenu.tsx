'use client';

import { useCallback, useState } from "react";
import { AiOutlineMenu } from "react-icons/ai";
import { useRouter } from "next/navigation";
 import useLoginModal from "@/app/hooks/useLoginModal";
import useRegisterModal from "@/app/hooks/useRegisterModal";
import useRentModal from "@/app/hooks/useRentModal";
import { signOut } from "next-auth/react";
// import { SafeUser } from "@/app/types";

import MenuItem from "./MenuItem";
import Avatar from "../Avatar";
import LoginModal from "@/app/modal/LoginModal";

interface CurrentUser {
  name?: string | null;
  email?: string | null;
  image?: string | null;
  createdAt?: string | null;
  updatedAt?: string | null;
  emailVerified?: string | null;
}

interface UserMenuProps {
  currentUser?: CurrentUser | null;
}


const UserMenu: React.FC<UserMenuProps> = ({
  currentUser
}) => {
//  console.log({currentUser});

   const loginModal = useLoginModal();
   const rentModal = useRentModal();

  const [isOpen, setIsOpen] = useState(false);

  const toggleOpen = useCallback(() => {
    setIsOpen((value) => !value);
  }, []);

  const onRent = useCallback(() => {
    if (!currentUser) {
      return loginModal.onOpen();
    }

    rentModal.onOpen();
  }, [loginModal, rentModal, currentUser]);


  return ( 
    <div className="relative">
      <div className="flex flex-row items-center gap-3">
        <div 
           onClick={onRent}
        //  onClick={() => router.push('/user')}
          className="
            hidden
            md:block
            text-sm 
            font-semibold 
            py-3 
            px-4 
            rounded-full 
            hover:bg-neutral-100 
            transition 
            cursor-pointer
          "
        >
          Rent room
        </div>
        <div 
       onClick={toggleOpen}
        // onClick={() => {}}
        className="
          p-4
          md:py-1
          md:px-2
          border-[1px] 
          border-neutral-200 
          flex 
          flex-row 
          items-center 
          gap-3 
          rounded-full 
          cursor-pointer 
          hover:shadow-md 
          transition
          "
        >
          <AiOutlineMenu />
          <div className="hidden md:block">
            {/* <Avatar src={currentUser?.image} /> */}
            <Avatar src={null}></Avatar>
          </div>
        </div>
      </div>
      {isOpen && (
        <div 
          className="
            absolute 
            rounded-xl 
            shadow-md
            w-[40vw]
            md:w-3/4 
            bg-white 
            overflow-hidden 
            right-0 
            top-12 
            text-sm
          "
        >
          <div className="flex flex-col cursor-pointer">
            {currentUser ? (
              <>
              <MenuItem 
                label="Logout" 
               onClick={() => signOut()}
          //  onClick={() => router.push('/properties')}
              
              />
            </>
             
               ) : (
                <>
              {/* <MenuItem 
                  label="Sign up" 
                  onClick={registerModal.onOpen}
                /> */}
                <MenuItem 
                  label="Login" 
                  onClick={loginModal.onOpen}
                />
                <hr />
               
              </> 
                )}
          </div>
        </div>
      )}
    </div>
   );
}
 
export default UserMenu;