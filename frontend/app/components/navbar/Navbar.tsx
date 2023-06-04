//import { SafeUser } from "@/app/types";

import Categories from "./Categories";
import Container from "../Container";
import Search from "./Search";
import UserMenu from "./UserMenu";

interface CurrentUser {
  name?: string | null;
  email?: string | null;
  image?: string | null;
  createdAt?: string | null;
  updatedAt?: string | null;
  emailVerified?: string | null;
}


interface NavbarProps {
  currentUser?: CurrentUser | null;
}


const Navbar: React.FC<NavbarProps> = ({
  currentUser,
}) => {
  console.log("Navbar");
  console.log(currentUser);
  return ( 
    <div className="fixed w-full bg-white z-10 shadow-sm">
      <div
        className="
          py-4 
          border-b-[1px]
        "
      >
      <Container>
        <div 
          className="
            flex 
            flex-row 
            items-center 
            justify-between
            gap-3
            md:gap-0
          "
        >
          <span></span>
          <Search />
          <UserMenu currentUser={currentUser}/>
        </div>
      </Container>
    </div>
    <Categories />
  </div>
  );
}


export default Navbar;