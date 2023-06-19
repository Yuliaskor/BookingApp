'use client';

import axios from "axios";
import { useCallback, useState } from "react";
import Modal from './Modal';
import Input from "../inputs/Input";
import Heading from "../Heading";
import Button from "../Button";

import { 
    FieldValues, 
    SubmitHandler,
    useForm
  } from "react-hook-form";


import { toast } from "react-hot-toast";
import { log } from "console";
import { useRouter } from 'next/navigation';
import useReserveModal from "../hooks/useReserveModal";
import { getSession } from "next-auth/react";
import CountrySelect from "../inputs/CountrySelect";
import Counter from "../inputs/Counter";
import { watch } from "fs";


const ReserveModal = () => {
    const router = useRouter();
    const reserveModal = useReserveModal();
    const [isLoading, setIsLoading] = useState(false);

    const { 
        register, 
        handleSubmit,
        setValue,
        watch,
        reset,
        formState: {
          errors,
        },
      } = useForm<FieldValues>({
        defaultValues: {
          email: '',
          password: '',
          guestCount: 1,
        },
      });
      const guestCount = watch('guestCount');

      const setCustomValue = (id: string, value: any) => {
        setValue(id, value, {
          shouldDirty: true,
          shouldTouch: true,
          shouldValidate: true
        })
      }

      const onSubmit: SubmitHandler<FieldValues> = async (data) => {
 
        // console.log(data);
        // console.log(category);
        // console.log()
        setIsLoading(true);


        const listingId = 1;

        const session = await getSession();
        const token = session.id_token;


        const photoList = [];
        photoList.push(data.imageSrc);

      
        // Adjust the data object to match the structure the API is expecting
        const postData = {
          tenantName: data.name,
          tenantEmail: data.email,
          checkInDate: reserveModal.checkInDate,
          checkOutDate: reserveModal.checkOutDate,
          numberOfGuests: guestCount
        };

        console.log(postData);

      
        axios.post(`http://localhost:8080/api/v1/listings/${reserveModal.listingId}/reservations`, postData)
          .then(() => {
            toast.success('Listing created!');
      
            router.refresh();
            reset();
            reserveModal.onClose();
          })
          .catch(() => {
            toast.error('Something went wrong.');
          })
          .finally(() => {
            setIsLoading(false);
          });
      }    
      // const onToggle = useCallback(() => {
      //   loginModal.onClose();
      //   registerModal.onOpen();
      // }, [loginModal, registerModal])
      const bodyContent = (
        <div className="flex flex-col gap-4">
          <Heading
            title="Welcome back"
            subtitle="Login to your account!"
          />
          <Input
            id="email"
            label="Email"
            disabled={isLoading}
            register={register}  
            errors={errors}
            required
          />
          <Input
            id="name"
            label="Name"
            disabled={isLoading}
            register={register}
            errors={errors}
            required
          />
          <Counter
             onChange={(value) => setCustomValue('guestCount', value)}
             value={guestCount}
             title="Guests" 
             subtitle="How many guests do you allow?"
          />
        </div>
      )
    
      const footerContent = (
        <div className="flex flex-col gap-4 mt-3">
          <hr />
         
          <div className="
          text-neutral-500 text-center mt-4 font-light">
            {/* <p>First time using Airbnb?
              <span 
                // onClick={onToggle} 
                className="
                  text-neutral-800
                  cursor-pointer 
                  hover:underline
                "
                > Create an account</span>
            </p> */}
          </div>
        </div>
      )
    
    return (
      <Modal
      disabled={isLoading}
      isOpen={reserveModal.isOpen}
      title="Reserve"
      actionLabel="Continue"
      onClose={reserveModal.onClose}
      onSubmit={handleSubmit(onSubmit)}
      body={bodyContent}
      footer={footerContent}
      />
    )
}

export default ReserveModal;


