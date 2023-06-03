'use client';

import { useCallback, useState } from "react";
import { AiOutlineMenu } from "react-icons/ai";
// import { signOut } from "next-auth/react";
import { useRouter } from "next/navigation";
 import useLoginModal from "@/app/hooks/useLoginModal";
import useRegisterModal from "@/app/hooks/useRegisterModal";
// import useRentModal from "@/app/hooks/useRentModal";
// import { SafeUser } from "@/app/types";

import MenuItem from "./MenuItem";
import Avatar from "../Avatar";
import LoginModal from "@/app/modal/LoginModal";

interface UserMenuProps {
 // currentUser?: SafeUser | null
  currentUser: null
}

const UserMenu= () => {
  const router = useRouter();

   const loginModal = useLoginModal();
  const registerModal = useRegisterModal();
//   const rentModal = useRentModal();

  const [isOpen, setIsOpen] = useState(false);

  const toggleOpen = useCallback(() => {
    setIsOpen((value) => !value);
  }, []);

//   const onRent = useCallback(() => {
//     if (!currentUser) {
//     //   return loginModal.onOpen();
//     }

//     rentModal.onOpen();
//   }, [loginModal, rentModal, currentUser]);

  return ( 
    <div className="relative">
      <div className="flex flex-row items-center gap-3">
        <div 
        //   onClick={onRent}
        // onClick={() => {}}
          className="
          md:block  
          text-sm
          font-semibold
          py-3
          px-4 
          "
        >
          User
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
             
              <>
              <MenuItem 
                  label="Sign up" 
                  onClick={registerModal.onOpen}
                />
                <MenuItem 
                  label="Login" 
                  onClick={loginModal.onOpen}
                />
                <hr />
                <MenuItem 
                  label="Logout" 
               //   onClick={() => signOut()}
               onClick={() => router.push('/properties')}
                
                />
              </> 
          </div>
        </div>
      )}
    </div>
   );
}
 
export default UserMenu;