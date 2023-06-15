'use client';

import Image from "next/image";
import { useRouter } from "next/navigation";
import { useCallback, useMemo } from "react";
import { format } from 'date-fns';

import useCountries from "@/app/hooks/useCountries";
// import { 
//   SafeListing, 
//   SafeReservation, 
//   SafeUser 
// } from "@/app/types";



import HeartButton from "../HeartButton";
import Button from "@/app/Button";
import ClientOnly from "../ClientOnly";

interface CurrentUser {
    name?: string | null;
    email?: string | null;
    image?: string | null;
    createdAt?: string | null;
    updatedAt?: string | null;
    emailVerified?: string | null;
  }

  interface ILocation {
    latitude: number;
    longitude: number;
    city: string;
    country: string;
    description: string;
  }
  
  interface IHost {
    id: number;
    email: string;
    name: string;
    aboutMe: string;
  }
  interface IListing {
    id: number;
    host: IHost;
    title: string;
    description: string;
    location: ILocation;
    photos: string;
    pricePerNight: number;
    maxGuests: number;
    availableFrom: string;
    availableTo: string;
    roomType: string;
    reservations: any[];
    beds: string[];
    amenities: string[];
    [key: string]: any;
  }

interface ListingCardProps {
  data: IListing;
  reservation?: IListing;
  onAction?: (id: string) => void;
  disabled?: boolean;
  actionLabel?: string;
  actionId?: string;
  currentUser?: CurrentUser | null
};

const ListingCard: React.FC<ListingCardProps> = ({
  data,
  reservation,
  onAction,
  disabled,
  actionLabel,
  actionId = '',
  currentUser,
}) => {
  const router = useRouter();
  const { getByValue } = useCountries();

  const location = getByValue(data.locationValue);

  const handleCancel = useCallback(
    (e: React.MouseEvent<HTMLButtonElement>) => {
    e.stopPropagation();

    if (disabled) {
      return;
    }

    onAction?.(actionId)
  }, [disabled, onAction, actionId]);

  const price = useMemo(() => {
    if (reservation) {
      return reservation.totalPrice;
    }

    return data.pricePerNight;
  }, [reservation, data.pricePerNight]);

  const reservationDate = useMemo(() => {
    if (!reservation) {
      return null;
    }
  
    const start = new Date(reservation.startDate);
    const end = new Date(reservation.endDate);

    return `${format(start, 'PP')} - ${format(end, 'PP')}`;
  }, [reservation]);

  return (
    <div 
      onClick={() => router.push(`/listings/${data.id}`)} 
      className="col-span-1 cursor-pointer group"
    >
      <div className="flex flex-col gap-2 w-full">
        <div 
          className="
            aspect-square 
            w-full 
            relative 
            overflow-hidden 
            rounded-xl
          "
        >
          <Image
            fill
            className="
              object-cover 
              h-full 
              w-full 
              group-hover:scale-110 
              transition
            "
           // src={data.imageSrc}
           src="https://res.cloudinary.com/dovtmuel7/image/upload/v1685968009/qhzkhlj5iwm1keyayomb.jpg"
            alt="Listing"
          />
          <div className="
            absolute
            top-3
            right-3
          ">
          </div>
        </div>
        <div className="font-semibold text-lg">
          {/* {location?.country}, {location?.label} */}
          Country, label
        </div>
        <div className="font-light text-neutral-500">
          {/* {reservationDate || data.category} */}
          Countrisyde
        </div>
        <div className="flex flex-row items-center gap-1">
          <div className="font-semibold">
            $ {price}
          </div>
          {!reservation && (
            <div className="font-light">night</div>
          )}
        </div>
        {onAction && actionLabel && (
          <Button
            disabled={disabled}
            small
            label={actionLabel} 
            onClick={handleCancel}
          />
        )}
      </div>
    </div>
   );
}
 
export default ListingCard;