'use client';

import Image from "next/image";

import useCountries from "@/app/hooks/useCountries";

import Heading from "@/app/Heading";
import HeartButton from "../HeartButton";


interface CurrentUser {
    name?: string | null;
    email?: string | null;
    image?: string | null;
    createdAt?: string | null;
    updatedAt?: string | null;
    emailVerified?: string | null;
  }

interface ListingHeadProps {
  title: string;
  locationValue: string;
  imageSrc: string;
  id: string;
  currentUser?: CurrentUser | null
}

const ListingHead: React.FC<ListingHeadProps> = ({
  title,
  locationValue,
  imageSrc,
  id,
  currentUser
}) => {
  const { getByValue } = useCountries();

  const location = getByValue(locationValue);

  return ( 
    <>
      <Heading
        title={title}
        subtitle={`${location?.region}, ${location?.label}`}
      />
      <div className="
          w-full
          h-[60vh]
          overflow-hidden 
          rounded-xl
          relative
        "
      >
        <Image
          src={imageSrc}
          fill
          className="object-cover w-full"
          alt="Image"
        />
      </div>
    </>
   );
}
 
export default ListingHead;